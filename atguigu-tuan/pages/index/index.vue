<template>
	<view class="gg" v-if="token">
		<!-- 利用background-image设置导航的线形渐变色彩 -->
		<u-navbar :is-back="false" :border-bottom="false" :background="{ 'background-image': 'linear-gradient(to right, rgb(255,180,61), rgb(255, 101, 0))' }">
			<view class="gg-map-slot-wrap u-font-xs u-m-l-20 u-p-l-10 u-p-r-10 u-p-t-5 u-p-b-5" @click="pickUpLocation">
				<u-icon name="map" size="24"></u-icon>
				<text class="u-p-l-10 u-p-r-10">{{ leaderAddressVo.takeName ? leaderAddressVo.takeName : '请设置提货点' }}</text>
				<u-icon name="arrow-right" size="20"></u-icon>
			</view>
		</u-navbar>

		<!-- 主内容区域-Begin -->
		<view class="gg-content">
			<!-- 头部区域 -->
			<view class="gg-header u-p-l-20 u-p-r-20">
				<!--
				头部滚动提示搜索区
				1.搜索图标
				2.滚动提示条
				3.搜索按钮（自定义样式）
				-->
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
					<u-button class="u-m-r-20" type="error" size="mini" shape="square" :custom-style="ggNoticeSearchBarRightBtnCustomStyle">搜索</u-button>
				</view>

				<!-- 新人专享低价好物sv滚动区 -->
				<view class="gg-new-vip u-p-20 u-m-t-20">
					<view class="u-font-lg u-content-color">新人专享低价好物</view>
					<scroll-view scroll-x enable-flex class="gg-new-vip-sv">
						<view class="u-flex u-m-t-10">
							<view class="gg-new-vip-sv-item u-p-r-20" v-for="(item, index) in newPersonSkuInfoList" :key="item.id" @click="gotoProductItem(item.id)">
								<ListImgItem
									:src="item.imgUrl"
									width="200rpx"
									height="200rpx"
									:showLeft="item.skuType === 1"
									:showRight="false"
									:showBottom="item.skuType === 0 && item.isNewPerson === 1"
								></ListImgItem>
								<text class="u-type-error">￥ {{ item.price }}</text>
								<AddToCart :shopDetail="item" :skuId="item.id"></AddToCart>
							</view>
						</view>
					</scroll-view>
				</view>
			</view>
			<!-- 头部区域底部左右圆角区 -->
			<view class="gg-header-bottom"></view>

			<!-- 硅谷优选规则 -->
			<view class="u-flex u-row-between u-p-20 u-m-20 gg-border" @click="showRulePopup = true">
				<u-image src="/static/logo.png" width="30rpx" height="30rpx"></u-image>
				<view>平台资质、法律条款、规则及投诉入口</view>
			</view>

			<!-- 商品分类 -->
			<view class="u-m-20 u-p-20 gg-border">
				<scroll-view enable-flex scroll-x @scroll="scrollMove">
					<view class="u-flex u-p-r-20 u-p-t-20">
						<!-- 循环滚动内容 -->
						<view class="u-p-l-20 u-p-r-20" v-for="item in categoryList" :key="item.id">
							<u-image :src="item.imgUrl" border-radius="30rpx" width="100rpx" height="100rpx"></u-image>
							<text class="u-font-xs">{{ item.name }}</text>
						</view>
					</view>
				</scroll-view>
				<view class="gg-category"><u-line-progress class="gg-category-progress" height="5" :show-percent="false" :percent="categorySVPercent"></u-line-progress></view>
			</view>

			<!-- 限时秒杀 -->
			<block v-if="seckillSkuVoList.length > 0">
				<!-- 秒杀 -->
				<view class="u-m-l-20 u-m-r-20 u-flex">
					<view class="u-flex u-flex-1">
						<view class="u-font-lg u-type-error">
							秒杀抢购：
							<text class="u-font-sm">{{ seckillTime.name }}场 {{ seckillTime.startTime }}-{{ seckillTime.endTime }}</text>
						</view>
					</view>
					<u-button :plain="true" size="mini" @click="gotoSeckill">查看全部 ></u-button>
				</view>
				<view class="u-m-20 u-p-20 gg-border">
					<scroll-view enable-flex scroll-x>
						<view class="u-flex u-p-r-20 u-p-t-20">
							<!-- 循环滚动内容 -->
							<view class="u-p-l-20 u-p-r-20 u-text-center" v-for="item in seckillSkuVoList" :key="item.skuId" @click="gotoProductItem(item.skuId)">
								<text class="u-font-sm u-m-b-5">{{ item.timeName }}</text>
								<u-image :src="item.imgUrl" border-radius="30rpx" width="200rpx" height="200rpx"></u-image>
								<text class="u-font-sm u-m-t-5">{{ item.skuName }}</text>
								<AddToCart :shopDetail="item" :skuId="item.skuId"></AddToCart>
							</view>
						</view>
					</scroll-view>
				</view>
			</block>

			<!-- 如何购买商品 -->
			<view class="u-p-20 u-m-20 gg-border u-font-xs">
				<view class="u-m-b-20">如何在[硅谷优选]购买商品</view>
				<view class="u-flex">
					<view class="gg-number u-m-r-10">1</view>
					挑商品
					<u-icon name="arrow-right-double u-m-l-10 u-tips-color"></u-icon>
					<u-icon class="u-content-color" name="arrow-right-double"></u-icon>
					<view class="gg-number gg-number-gray u-m-r-10 u-m-l-10">2</view>
					选提货点
					<u-icon name="arrow-right-double u-m-l-10 u-tips-color"></u-icon>
					<u-icon class="u-content-color" name="arrow-right-double"></u-icon>
					<view class="gg-number gg-number-gray u-m-r-10 u-m-l-10">3</view>
					次日16点提货点取货
				</view>
			</view>

			<!-- 热销好货 -->
			<view class="u-font-xl u-type-error u-m-20">热销好货</view>
			<view class="u-p-20 u-m-20 gg-border" v-for="(item, index) in hotSkuList" :key="item.id" @click="gotoProductItem(item.id)">
				<view class="u-m-b-10 u-m-l-20 u-m-r-20 u-flex gg-product-item">
					<ListImgItem
						:src="item.imgUrl"
						width="250rpx"
						height="250rpx"
						:showLeft="item.skuType === 1"
						:showRight="false"
						:showBottom="item.skuType === 0 && item.isNewPerson === 1"
					></ListImgItem>
					<view class="gg-product-item-msg u-border-bottom u-p-b-20 u-m-l-20">
						<view class="gg-product-item-msg-title">
							<view class="u-font-lg">{{ item.title }}</view>
							<view class="u-type-info u-font-sm">已售{{ item.sale }}/剩余{{ item.stock }}</view>
							<block v-if="item.ruleList">
								<view class="u-font-xs u-type-error-dark" v-for="(rule, ruleIndex) in item.ruleList" :key="ruleIndex">{{ rule }}</view>
							</block>
						</view>
						<view class="u-flex u-row-between">
							<view class="u-type-error gg-product-item-msg-price">
								<text>￥</text>
								<text class="gg-product-item-msg-price-value">{{ item.price }}</text>
							</view>
							<AddToCart :shopDetail="item"></AddToCart>
						</view>
					</view>
				</view>
			</view>

			<u-gap height="20"></u-gap>
		</view>
		<!-- 主内容区域-End -->

		<!-- 平台资质、法律条款、规则及投诉入口弹出框 -->
		<u-popup v-model="showRulePopup" mode="bottom" border-radius="20" :closeable="true">
			<view class="u-p-t-20 u-p-b-20">
				<view class="u-m-20">
					<view class="u-font-xl u-m-b-10">
						<u-icon name="checkmark-circle" color="#dd6161" size="28" class="u-m-r-10"></u-icon>
						品质保障
					</view>
					<view class="u-light-color u-font-xs">全场商品均经过品质检验，若收货时发现商品有变质、腐烂、损坏等情况，可申请退款</view>
				</view>

				<view class="u-m-20">
					<view class="u-font-xl u-m-b-10">
						<u-icon name="kefu-ermai" color="#dd6161" size="28" class="u-m-r-10"></u-icon>
						极速退款
					</view>
					<view class="u-light-color u-font-xs">根据平台的规则，在一定条件下，可享受极速退款到账服务</view>
				</view>

				<view class="u-m-20">
					<view class="u-font-xl u-m-b-10">
						<u-icon name="bag" color="#dd6161" size="28" class="u-m-r-10"></u-icon>
						次日自提
					</view>
					<view class="u-light-color u-font-xs">每日23点前下单，次日16点可到下单门店自提</view>
				</view>

				<view class="u-m-20">
					<view class="u-font-xl u-m-b-10">
						<u-icon name="integral" color="#dd6161" size="28" class="u-m-r-10"></u-icon>
						资质规则
					</view>
					<view class="u-light-color u-font-xs">平台资质、法律条款、规则及投诉入口，点击查看详情</view>
				</view>
			</view>
		</u-popup>
	</view>

	<view v-else class="emptyPage"><u-empty mode="page"></u-empty></view>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex';
let watchTimes = 0;
export default {
	data() {
		return {
			list: ['寒雨连江夜入吴', '平明送客楚山孤', '洛阳亲友如相问', '一片冰心在玉壶'],
			// 滚动提示搜索区右侧按钮自定义样式
			ggNoticeSearchBarRightBtnCustomStyle: {
				borderRadius: '30rpx'
			},
			showRulePopup: false,
			windowWidth: 0, // 设备宽度
			categorySVPercent: 0, // 精选进度百分比
			token: null
		};
	},
	onLoad() {
		uni.getSystemInfo({
			success: res => {
				this.windowWidth = res.windowWidth;
			}
		});
	},
	computed: {
		...mapState('indexModule', ['home']),
		...mapGetters('indexModule', ['categoryList', 'hotSkuList', 'newPersonSkuInfoList', 'seckillTime', 'seckillSkuVoList', 'leaderAddressVo'])
	},
	methods: {
		...mapActions('indexModule', ['getHomeIndexAction']),
		scrollMove(e) {
			// 计算优选滚动的进度百分比
			let scrollLeft = e.detail.scrollLeft;
			let scrollWidth = e.detail.scrollWidth - this.windowWidth;
			let percent = parseInt((scrollLeft / scrollWidth) * 100);

			// 利用防抖进行性能的优化
			this.$u.debounce(() => {
				this.categorySVPercent = percent;
			}, 100);
		},
		// 跳转至设置提货点
		pickUpLocation() {
			this.$u.route('/pagesLocation/myPickUpLocation/myPickUpLocation');
		},
		// 跳转至商品详情页
		gotoProductItem(skuId) {
			this.$u.route('/pages/homeItem/homeItem', { skuId });
		},
		// 跳转至秒杀页
		gotoSeckill() {
			this.$u.route('/pagesSeckill/seckill/seckill');
		}
	},
	watch: {
		/* ------------------------------------------------------------
		watch监控第一部分，函数式监控
		1.只能监控单层数据，不能立即监控，只有数据变化的时候才可以
		2.深度监控无法实现
		3.函数不可以使用箭头函数，否则this对象无法找到
		------------------------------------------------------------ */
		// leaderAddressVo: function() {
		// 	console.log('normal watch', this.leaderAddressVo);
		// }
		/* ------------------------------------------------------------
		watch监控第二部分，对象式监控
		1.immediate立即监控的触发次数，无论如何先会触发一次，监控数据变化
		2.深度监控的作用，直接监控对象属性值的变化
		3.监控次数的判断
			1)因为立即监控，所以页面初始会触发一次
			2)可以设置全局变量进行判断条件依据
			3)利用监控次数确认路由的跳转
		------------------------------------------------------------ */
		// 'leaderAddressVo.userId': {
		// 	handler(newVal) {
		// 		watchTimes++;
		// 		if (watchTimes > 1) {
		// 			watchTimes = 0;
		// 			if (!newVal) {
		// 				console.log('immediate deep watch');
		// 			}
		// 		}
		// 	},
		// 	immediate: true,
		// 	deep: true
		// }
		/* ------------------------------------------------------------
		watch监控第三部分，对象式监控
		1.immediate的去除
		2.等mounted请求结束，数据设置完毕以后再进行数据变化的监控
		3.不需要进行监控次数的判断
		------------------------------------------------------------ */
		'leaderAddressVo.userId': {
			handler(newVal) {
				if (!newVal) {
					uni.redirectTo({
						url: '/pagesLocation/myPickUpLocation/myPickUpLocation'
					});
				}
			},
			deep: true
		}
	},
	onShow() {
		/* 
		 onShow比mounted先执行
		 mounted中有获取数据的异步请求，本身是异步，还需要再进行数据设置获取
		 onShow已经执行完毕，是否能确认leaderAddressVo对象的内容变化？不能
		 是否能在onShow中利用leaderAddressVo进行条件判断？不能
		*/
		console.log('leaderAddressVo:', this.leaderAddressVo);
	},
	async mounted() {
		console.log('mounted');
		const token = await uni.getStorageSync('token');
		if (this.$u.test.isEmpty(token)) {
			uni.reLaunch({
				url: '/pages/login/login'
			});
			return;
		}

		this.token = token;
		await this.getHomeIndexAction();
	}
};
</script>

<style lang="scss" scoped>
/* 
gg为硅谷guigu前缀，利用scss中&-的方式实现层级样式的拼接
*/
.gg {
	/* navbar 导航中进行地图位置的获取设置 */
	&-map-slot-wrap {
		display: flex;
		align-items: center;
		background-color: rgba(240, 240, 240, 0.35);
		color: #fff;
		border-radius: 100rpx;
	}

	/* 内容区域 */
	&-content {
		background-color: $u-bg-color;
	}

	/* 
		notice-bar头部滚动信息搜索区域
		利用background-image的linear-gradient保持与导航一致的线形渐变色彩 
	*/
	&-header {
		background-image: linear-gradient(to right, rgb(255, 180, 61), rgb(255, 101, 0));
		height: 500rpx;
	}

	/* 滚动信息搜索框 */
	&-notice-search-bar {
		display: flex;
		align-items: center;
		background-color: white;
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

	/* 头部区域底部左右圆角区域 */
	&-header-bottom {
		background-image: linear-gradient(to right, rgb(255, 180, 61), rgb(255, 101, 0));
		height: 25rpx;
		border-bottom-left-radius: 25rpx;
		border-bottom-right-radius: 25rpx;
	}

	/* 新人专享 */
	&-new-vip {
		background-color: white;
		border-radius: 20rpx;
		&-sv {
			height: 320rpx;
			&-item {
				display: flex;
				flex-direction: column;
			}
		}
	}

	/* 硅谷优选规则 */
	&-border {
		border-radius: 20rpx;
		border-bottom: 1rpx solid #f5f5f5;
		box-shadow: 0px 1px 20px 0px rgba(0, 0, 0, 0.1); /*下边阴影 */
	}

	/* 商品分类 */
	&-category {
		display: flex;
		justify-content: center;
		align-items: center;
		height: 5rpx;
		&-progress {
			width: 10vw;
		}
	}

	/* 如何购买商品 */
	&-number {
		border-radius: 50%;
		background-color: $u-type-warning;
		width: 30rpx;
		height: 30rpx;
		text-align: center;
		font-size: 20rpx;
		color: white;
		&-gray {
			background-color: gray;
		}
	}

	/* 热销好货 */
	&-product-item {
		height: 250rpx;
		&-msg {
			height: 100%;
			flex: 1;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			&-price {
				&-value {
					font-size: 50rpx;
				}
			}
		}
	}
}

.emptyPage {
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	height: 100vh;
}
</style>
