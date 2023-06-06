<template>
	<block v-if="empty">
		<u-navbar :is-back="true" back-icon-color="#fff" :border-bottom="false" :background="{ background: 'rgb(255, 101, 0)' }">
			<view class="u-font-xl u-flex u-row-center u-type-info-light u-flex-1"><view>限时秒杀</view></view>
		</u-navbar>
		<u-empty mode="data"></u-empty>
	</block>

	<block v-else>
		<view class="gg">
			<!-- 利用background-image设置导航的线形渐变色彩 -->
			<u-navbar :is-back="true" back-icon-color="#fff" :border-bottom="false" :background="{ background: 'rgb(255, 101, 0)' }">
				<view class="u-font-xl u-flex u-row-center u-type-info-light u-flex-1"><view>限时秒杀</view></view>
			</u-navbar>
			<view class="gg-content">
				<view class="gg-content-header">
					<scroll-view enable-flex scroll-x :scroll-into-view="`scroll-${navId}`">
						<view class="u-flex u-flex-nowrap u-p-r-20 u-p-t-20">
							<!-- 循环滚动内容 -->
							<view
								class="u-p-l-20 u-p-r-20"
								v-for="seckillTime in seckillTimeList"
								:key="seckillTime.id"
								@click="changeNav(seckillTime.id, seckillTime.name)"
								:id="`scroll-${seckillTime.id}`"
							>
								<view style="width: 140rpx;" class="u-text-center">
									<view class="u-font-xl u-type-info-light">{{ seckillTime.name }}</view>
									<!-- 1：已开抢 2：抢购中 3：即将开抢 -->
									<block v-if="seckillTime.timeStaus === 1">
										<u-tag
											:bg-color="seckillTime.id === navId ? '#ff0000' : '#fff'"
											:color="seckillTime.id === navId ? '#fff' : '#555'"
											text="已开抢"
											mode="dark"
											shape="circle"
										/>
									</block>
									<block v-if="seckillTime.timeStaus === 2">
										<u-tag
											:bg-color="seckillTime.id === navId ? '#19be6b' : '#fff'"
											:color="seckillTime.id === navId ? '#fff' : '#555'"
											text="抢购中"
											mode="dark"
											shape="circle"
										/>
									</block>
									<block v-if="seckillTime.timeStaus === 3">
										<u-tag
											:bg-color="seckillTime.id === navId ? '#fc0' : '#fff'"
											:color="seckillTime.id === navId ? '#fff' : '#555'"
											text="即将开抢"
											mode="dark"
											shape="circle"
										/>
									</block>
								</view>
							</view>
						</view>
					</scroll-view>

					<view class="gg-sv-seckill-container">
						<view class="u-m-20 u-flex u-flex-1 gg-sv-list-container">
							<scroll-view scroll-y="true" class="gg-sv-list">
								<view class="gg-sv-item" v-for="seckillSku in seckillSkuList" :key="seckillSku.id">
									<u-image :src="seckillSku.imgUrl" width="100%" height="400rpx" border-radius="10rpx" />
									<view class="u-font-xl u-p-t-10 u-p-b-10">{{ seckillSku.skuName }}</view>
									<view>限购数量：{{ seckillSku.seckillLimit }} 销售量：{{ seckillSku.seckillSale }} 库存：{{ seckillSku.seckillStock }}</view>
									<view class="u-flex u-flex-1 u-row-between u-p-t-10 ">
										<view>
											价格：
											<text class="u-font-xl u-type-error">￥{{ seckillSku.seckillPrice }}</text>
										</view>
										<view><u-button type="error" size="medium" shape="circle" @click="gotoProductItem(seckillSku.skuId)">立即抢购</u-button></view>
									</view>
								</view>
							</scroll-view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</block>
</template>

<script>
export default {
	data() {
		return {
			seckillTimeList: [],
			timeName: '',
			navId: 0,
			seckillSkuList: [],
			empty: false
		};
	},
	methods: {
		// 秒杀时间段获取
		async getSeckillTimeList() {
			const result = await this.$u.api.getFindAllSeckillTimeList();
			this.seckillTimeList = result;
		},
		// 秒杀商品列表
		async geSeckillSkuList(timeName) {
			const result = await this.$u.api.getFindSeckillSkuList({ timeName });
			this.seckillSkuList = result;
		},
		// 跳转至商品详情
		gotoProductItem(id) {
			this.$u.route('/pages/homeItem/homeItem?skuId=' + id);
		},
		// 秒杀时间段进行导航切换
		changeNav(navId, timeName) {
			this.navId = navId;
			this.geSeckillSkuList(timeName);
		},
	},
	async mounted() {
		// 获取秒杀时间段与第一个时间段的商品列表
		await this.getSeckillTimeList();
		if (this.seckillTimeList.length > 0) {
			this.navId = this.seckillTimeList[0].id;
			this.timeName = this.seckillTimeList[0].name;
			this.geSeckillSkuList(this.timeName);
		} else {
			this.empty = true;
		}
	}
};
</script>

<style lang="scss">
body {
	background-color: $u-bg-color;
}
.gg {
	&-content {
		&-header {
			background-color: rgb(255, 101, 0);
			height: 300rpx;
			border-bottom-left-radius: 80rpx;
			border-bottom-right-radius: 80rpx;
		}
	}

	&-sv-seckill-container {
		position: absolute;
		z-index: 1;
		top: 280rpx;
		width: 100%;
	}

	&-sv-list-container {
		background-color: $u-bg-color;
		border-radius: 20rpx;
	}

	&-sv-list {
		height: calc(100vh - 380rpx);
		padding: 20rpx;
	}

	&-sv-item {
		padding: 20rpx;
		border-bottom: 10rpx solid white;
		border-radius: 20rpx;
		background-color: white;
		margin-bottom: 20rpx;
	}

	.active {
		background-color: red !important;
	}
}
</style>
