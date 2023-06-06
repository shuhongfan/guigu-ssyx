<template>
	<view class="gg">
		<u-navbar :is-back="false" :border-bottom="false">
			<view class="u-flex u-m-l-20 u-m-r-20 gg-search-navbar">
				<view class="u-font-xl u-m-r-20">分类</view>
				<view class="gg-notice-search-bar">
					<u-icon name="search" class="gg-notice-search-bar-left-icon"></u-icon>
					<u-notice-bar
						class="gg-notice-search-bar-u-notice-bar"
						type="none"
						mode="vertical"
						:is-circular="false"
						:list="list"
						:border-radius="30"
						:volume-icon="false"
						:more-icon="false"
					></u-notice-bar>
				</view>
			</view>
		</u-navbar>

		<view class="gg-menu-wrap">
			<scroll-view scroll-y class="gg-tab-view menu-scroll-view" scroll-with-animation :scroll-top="scrollTop">
				<view
					v-for="(item, index) in categories"
					:key="item.id"
					class="gg-tab-item"
					:class="[current === index ? 'gg-tab-item-active' : '']"
					:data-current="index"
					@tap.stop="swichCategory(item.id, index)"
				>
					<text class="u-line-1">{{ item.name }}</text>
				</view>
			</scroll-view>

			<scroll-view scroll-y class="gg-right-box" @scrolltolower="loadMore">
				<view v-for="(item, index) in searchResult.content" :key="item.id"  @click="gotoProductItem(item.id)">
					<view class="u-m-b-10 u-m-l-20 u-m-r-20 u-flex gg-product-item">
						<ListImgItem
							:src="item.imgUrl"
							width="200rpx"
							height="200rpx"
							:showLeft="item.skuType === 1"
							:showRight="false"
							:showBottom="item.skuType === 0 && item.isNewPerson === 1"
						>
						<template #left>秒杀商品</template>
						</ListImgItem>
						<view class="gg-product-item-msg u-border-bottom u-p-b-20 u-m-l-20">
							<view>
								<view class="u-font-lg">{{ item.title }}</view>
								<view class="u-type-info u-font-sm">已售{{ item.sale }}/剩余{{ item.stock }}</view>
								<block v-if="item.ruleList">
									<view class="u-font-xs u-type-error-dark" v-for="(rule, ruleIndex) in item.ruleList" :key="ruleIndex">{{ rule }}</view>
								</block>
							</view>
							<view class="u-flex u-row-between">
								<view class="u-type-error gg-product-item-msg-price-container">
									<text>￥</text>
									<text class="gg-product-item-msg-price-container-value">{{ item.price }}</text>
								</view>
								<AddToCart :shopDetail="item"></AddToCart>
							</view>
						</view>
					</view>
					<u-gap height="20"></u-gap>
				</view>

				<!-- 如果列表没有更多数据，则显示分隔线 -->
				<u-divider v-if="!(searchResult.first && searchResult.empty) && searchResult.last" :height="60" bg-color="transparent">我是有底线的</u-divider>
				<!-- 如果列表没有数据，则显示空内容 -->
				<u-empty mode="list" :show="searchResult.first && searchResult.empty"></u-empty>
			</scroll-view>
		</view>
	</view>
</template>

<script>
import { mapState, mapActions } from 'vuex';
export default {
	data() {
		return {
			list: ['最见搜索关键字', '暂时未处理搜索页', '可以考虑最见关键字'],
			current: 0, // 预设当前项的值
			categoryId: 0, // 当前选中的分类Id
			scrollTop: 0, //tab标题的滚动条位置
			menuHeight: 0, // 左边菜单的高度
			menuItemHeight: 0, // 左边菜单item的高度
			filter: {
				page: 1, // 当前页码
				limit: 5, // 每页记录数
				keyword: '', // 关键字
				wareId: ''
			},
			searchResult: {
				content: [],
				last: false
			} // 搜索商品结果对象
		};
	},
	computed: {
		...mapState('categoriesModule', ['categories'])
	},
	methods: {
		...mapActions('categoriesModule', ['getCategoriesAction']),
		...mapActions('cartModule', ['getCartListAction']),
		/*
		为什么不使用scroll-into-view而使用手动计算的方式，
		这是因为scroll-into-view不会进行居中菜单位置的定位处理，
		为了更好的用户操作体验，可以让当前选中菜单定位于整体滚动的居中位置
		*/
		// 点击左边的栏目切换
		async swichCategory(categoryId, index) {
			Object.assign(this.$data.searchResult, this.$options.data().searchResult); // 这里重置 searchResult 下的所有数据
			Object.assign(this.$data.filter, this.$options.data().filter); // 这里重置 filter 下的所有数据

			this.categoryId = categoryId;
			if (index == this.current) return; // 防止选中分类的再次点击
			this.current = index;
			// 如果为0，意味着尚未初始化
			if (this.menuHeight == 0 || this.menuItemHeight == 0) {
				await this.getElRect('menu-scroll-view', 'menuHeight');
				await this.getElRect('gg-tab-item', 'menuItemHeight');
			}
			// 将菜单菜单活动item垂直居中
			this.scrollTop = index * this.menuItemHeight + this.menuItemHeight / 2 - this.menuHeight / 2;

			// 获取右侧商品搜索结果数据
			this.getCategoryProductList();
		},
		// 获取一个目标元素的高度
		getElRect(elClass, dataVal) {
			new Promise((resolve, reject) => {
				const query = uni.createSelectorQuery().in(this);
				query
					.select('.' + elClass)
					.fields({ size: true }, res => {
						// 如果节点尚未生成，res值为null，循环调用执行
						if (!res) {
							setTimeout(() => {
								this.getElRect(elClass);
							}, 10);
							return;
						}
						this[dataVal] = res.height;
					})
					.exec();
			});
		},
		// 获取分类商品列表
		async getCategoryProductList() {
			const o = {
				categoryId: this.categoryId,
				page: this.filter.page,
				keyword: this.filter.keyword,
				limit: this.filter.limit,
				wareId: this.filter.wareId
			};
			let result = await this.$u.api.getSearchSku(o);
			this.searchResult = { ...result, content: [...this.searchResult.content, ...result.content] };
		},
		// 加载更多数据
		loadMore() {
			if (!this.searchResult.last) {
				this.filter.page = this.filter.page + 1;
				this.getCategoryProductList();
			}
		},
		// 跳转到商品详情页
		gotoProductItem(skuId){
			this.$u.route('/pages/homeItem/homeItem', {
				skuId
			});
		}
	},
	async mounted() {
		await this.getCategoriesAction(); // 从仓库中获取分类列表
		this.categoryId = this.categories[0].id; // 获取第一个分类的id
		await this.getCategoryProductList(); // 商品数据搜索请求
		// 需要获取购物车数据列表，将最新获取的数据渲染到页面
		this.getCartListAction();
	}
};
</script>

<style lang="scss" scoped>
.gg {
	height: calc(100vh);
	display: flex;
	flex-direction: column;

	&-search-navbar {
		margin-top: -20rpx;
		width: 100%;
	}
	/* 滚动信息搜索框 */
	&-notice-search-bar {
		flex: 1;
		display: flex;
		align-items: center;
		background-color: #ededed;
		border-radius: 30rpx;

		/* 滚动信息搜索框左侧图标 */
		&-left-icon {
			position: relative;
			left: 20rpx;
			color: $u-light-color;
		}

		/* 滚动信息搜索框中的字体设置 */
		&-u-notice-bar {
			flex: 1;
			/* 深层穿透修改子组件字体颜色样式 */
			::v-deep .u-news-item {
				color: $u-light-color !important;
			}
		}
	}

	/* 菜单包装器 */
	&-menu-wrap {
		flex: 1;
		display: flex;
		overflow: hidden;
	}

	/* 左侧sv选项卡模式菜单 */
	&-tab-view {
		width: 200rpx;
		height: 100%;
	}

	/* 左侧菜单选项卡项 */
	&-tab-item {
		height: 110rpx;
		background: #f6f6f6;
		box-sizing: border-box;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 26rpx;
		color: #444;
		font-weight: 400;
		line-height: 1;

		/* 激活状态 */
		&-active {
			position: relative;
			color: $u-type-warning;
			font-size: 30rpx;
			font-weight: 600;
			background: #fff;
			/* 伪类处理 */
			&::before {
				content: '';
				position: absolute;
				border-left: 4px solid $u-type-warning;
				height: 32rpx;
				left: 0;
				top: 39rpx;
			}
		}
	}

	/* 产品列表项 */
	&-product-item {
		height: 210rpx;

		&-msg {
			height: 100%;
			flex: 1;
			display: flex;
			flex-direction: column;
			justify-content: space-between;

			&-price-container {
				&-value {
					font-size: 40rpx;
				}
			}
		}
	}

	&-right-box {
		background-color: rgb(250, 250, 250);
	}
}
</style>
