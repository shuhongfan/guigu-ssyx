<template>
	<view class="gg" v-if="!detail.skuInfoVo">
		<u-empty mode="data"></u-empty>
	</view>
	<view class="gg" v-else>
		<u-navbar :is-back="true" :border-bottom="false" class="navbar"></u-navbar>

		<scroll-view scroll-y class="gg-sv">
			<view class="gg-content">
				<!-- 商品轮播图 -->
				<block v-if="swiperList.length > 0"><u-swiper :list="swiperList" :height="500" mode="number" indicator-pos="bottomRight"></u-swiper></block>

				<!-- 商品缩略图及属性还有价格购买数量的显示 -->
				<view class="u-flex u-type-info-light u-p-20 gg-content-price">
					<view class="u-p-r-20">
						<ListImgItem
							:src="detail.skuInfoVo.imgUrl"
							width="120rpx"
							height="120rpx"
							:showLeft="detail.skuInfoVo.skuType === 1"
							:showRight="false"
							:showBottom="detail.skuInfoVo.skuType === 0 && detail.skuInfoVo.isNewPerson === 1"
						></ListImgItem>
					</view>
					<!-- 区别秒杀商品与一般商品的价格体系 -->
					<!-- 秒杀商品显示 -->
					<block v-if="detail.skuInfoVo.skuType === 1">
						<view>
							<view class="gg-content-price-current u-flex">
								<view>秒杀价格：￥{{ detail.seckillSkuVo.seckillSale }}</view>
								<view class="u-p-l-20">市场价格：￥{{ detail.skuInfoVo.marketPrice }}</view>
							</view>
							<view class="gg-content-price-limit">
								<view>
									限购：{{ detail.seckillSkuVo.seckillLimit }}件 销售：{{ detail.seckillSkuVo.seckillSale }}件 库存：{{ detail.seckillSkuVo.seckillStock }}件
								</view>
								<view>
									秒杀场次：{{ detail.seckillSkuVo.timeName }} 倒计时：
									<u-count-down ref="seckillCountDown" :timestamp="seckillSeconds" @end="seckillEnd"></u-count-down>
								</view>
							</view>
						</view>
					</block>
					<!-- 普通商品显示 -->
					<block v-else>
						<view>
							<view class="gg-content-price-current u-flex">
								<view>当前价格：￥{{ detail.skuInfoVo.price }}</view>
								<view class="u-p-l-20">市场价格：￥{{ detail.skuInfoVo.marketPrice }}</view>
							</view>
							<view class="gg-content-price-limit">
								<view>限购数量：{{ detail.skuInfoVo.perLimit }}件</view>
							</view>
						</view>
					</block>
				</view>

				<!-- 商品名称 -->
				<view class="u-m-20 u-p-20 gg-content-container">
					<view class="u-flex">{{ detail.skuInfoVo.skuName }}</view>
				</view>

				<!-- 属性列表 -->
				<view class="u-m-20 u-p-20 gg-content-container" v-if="detail.skuInfoVo.skuAttrValueList.length > 0">
					<view class="u-p-t-5" v-for="skuAttrValueItem in detail.skuInfoVo.skuAttrValueList" :key="skuAttrValueItem.id">
						{{ skuAttrValueItem.attrName }}：{{ skuAttrValueItem.attrValue }}
					</view>
				</view>

				<!-- 活动规则 -->
				<view class="u-m-20 u-p-20 gg-content-container" v-if="detail.activityRuleList.length > 0">
					<view class="u-p-t-5" v-for="activityRuleItem in detail.activityRuleList" :key="activityRuleItem.id">{{ activityRuleItem.ruleDesc }}</view>
				</view>

				<!-- 售后与提货地址 -->
				<view class="u-m-20 u-p-20 gg-content-container">
					<view class="u-flex">
						<view class="u-font-sm u-m-r-20 u-type-info">售后无忧</view>
						<view class="u-font-sm u-m-r-20 u-flex-1">
							支持保价
							<text class="u-type-error">・</text>
							极速退款
							<text class="u-type-error">・</text>
							7天无理由退货
						</view>
						<u-icon name="arrow-right" size="24" class="u-m-r-20 u-type-info"></u-icon>
					</view>

					<view class="u-flex u-m-t-20">
						<view class="u-font-sm u-m-r-20 u-type-info">提货信息</view>
						<view class="u-font-sm u-m-r-20 u-flex-1">
							<view>
								现在下单，
								<text class="u-type-error">明天16:00可提货</text>
							</view>
							<view>提货点：{{ leaderAddressVo.takeName ? leaderAddressVo.takeName : '请设置提货点' }}</view>
						</view>
						<u-icon name="arrow-right" size="24" class="u-m-r-20 u-type-info"></u-icon>
					</view>
				</view>

				<!-- 商品详情图片清单 -->
				<view class="u-m-20 u-p-20 gg-content-container" v-if="skuPosterList.length > 0">
					<image v-for="skuPoster in skuPosterList" :key="skuPoster.id" :src="skuPoster.image"></image>
				</view>
				<u-gap height="30"></u-gap>
			</view>
		</scroll-view>

		<view class="gg-navigation">
			<view class="navigation">
				<view class="left">
					<view class="item" @click="gotoHome">
						<u-icon name="home-fill" :size="40" :color="$u.color['contentColor']"></u-icon>
						<view class="text u-line-1">首页</view>
					</view>
					<view class="item car">
						<u-badge class="car-num" :count="getProductSkuNum(skuId)" v-if="skuId !== 0" type="error" :offset="[-3, -6]"></u-badge>
						<u-icon name="shopping-cart-fill" :size="40" :color="$u.color['contentColor']"></u-icon>
						<view class="text u-line-1">购物车</view>
					</view>

					<view class="item car" v-if="detail.couponInfoList.length > 0" @click="showCouponInfoList = true">
						<u-badge class="car-num" :count="detail.couponInfoList.length" type="error" :offset="[-3, -6]"></u-badge>
						<u-icon name="gift-fill" :size="40" :color="$u.color['contentColor']"></u-icon>
						<view class="text u-line-1">优惠券</view>
					</view>
				</view>
				<!-- 即将秒杀与加入购物车的显示 -->
				<view class="right u-p-r-10 u-flex" v-if="!seckillEnded">
					<!-- 即将开抢 -->
					<block v-if="getDetail.seckillSkuVo.timeStaus === 3">
						<view class="u-m-r-20 u-line-1  u-flex-1 u-text-center">场次:{{ getDetail.seckillSkuVo.timeName }}</view>
						<view class="buy-disabled btn u-line-1">即将开抢</view>
					</block>
					<!-- 加入购物车 -->
					<block v-else>
						<view class="u-m-r-20 u-line-1  u-flex-1"><AddToCart :shopDetail="getDetail" :skuId="detail.skuInfoVo.id" v-if="detail.skuInfoVo.id"></AddToCart></view>
						<view class="buy btn u-line-1">立即购买</view>
					</block>
				</view>
				<!-- 秒杀结束显示 -->
				<view class="right u-p-r-10 u-flex" v-else>
					<view class="u-m-r-20 u-line-1 u-flex-1"></view>
					<view class="buy-disabled btn u-line-1">秒杀结束</view>
				</view>
			</view>
		</view>

		<CouponInfoList :showCouponInfoList.sync="showCouponInfoList" :couponInfoList="detail.couponInfoList" @getCouponInfo="getCouponInfo"></CouponInfoList>
	</view>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
export default {
	data() {
		return {
			detail: {},
			skuId: 0,
			seckillSeconds: 0,
			seckillEnded: false,
			showCouponInfoList: false
		};
	},
	computed: {
		...mapState('pickUpLocationModule', ['leaderAddressVo']),
		...mapGetters('cartModule', ['getProductSkuNum']),
		// 轮播图的获取
		swiperList() {
			let lst = [];
			this.detail &&
				this.detail.skuInfoVo &&
				this.detail.skuInfoVo.skuImagesList.length > 0 &&
				this.detail.skuInfoVo.skuImagesList.forEach(item => {
					lst.push({
						image: item.imgUrl,
						title: item.imgName
					});
				});
			return lst;
		},
		// 商品详情海报图片列表
		skuPosterList() {
			let lst = [];
			this.detail &&
				this.detail.skuInfoVo &&
				this.detail.skuInfoVo.skuPosterList.length > 0 &&
				this.detail.skuInfoVo.skuPosterList.forEach(item => {
					lst.push({
						image: item.imgUrl,
						title: item.imgName
					});
				});
			return lst;
		},
		getDetail() {
			return { ...this.detail.skuInfoVo, seckillSkuVo: this.detail.seckillSkuVo };
		}
	},
	methods: {
		async getHomeItemData(skuId) {
			let result = await this.$u.api.getHomeItem({ skuId });
			this.detail = result;
			// skuInfoVo 商品信息
			// couponInfoList 优惠券信息
			// activityRuleList 活动信息

			// 如果是秒杀商品，需要计算倒计时秒数
			if (this.detail.skuInfoVo.skuType === 1) {
				const now = this.$dayjs();
				const endTime = this.$dayjs(this.$dayjs().format('YYYY-MM-DD') + ' ' + this.detail.seckillSkuVo.endTime);
				const seconds = endTime.diff(now, 'second');
				this.seckillSeconds = seconds;
			}
		},
		// 切换首页
		gotoHome() {
			uni.switchTab({
				url: '/pages/index/index'
			});
			return false;
		},
		// 秒杀倒计时结束
		seckillEnd() {
			this.seckillEnded = true;
		},
		// 领取优惠券
		async getCouponInfo(id) {
			try {
				const result = await this.$u.api.getCouponInfo({ id });
				const pos = this.detail.couponInfoList.findIndex(item => item.id === id);
				this.$set(this.detail.couponInfoList[pos], 'couponStatus', 1);
			} catch (e) {
				console.error(e);
			}
		}
	},
	onLoad(options) {
		let skuId = +options.skuId;
		this.skuId = skuId;
		this.getHomeItemData(skuId);
	}
};
</script>

<style lang="scss">
.gg {
	height: 100%;

	&-sv {
		height: calc(100vh - 116rpx - 102rpx);
	}

	&-content {
		background-color: $u-bg-color;

		&-price {
			background: linear-gradient(to right, rgb(255, 180, 61), rgb(255, 101, 0));
		}

		&-container {
			z-index: 9999;
			background-color: #fff;
			border-radius: 25rpx;
		}
	}

	&-navigation {
		position: fixed;
		bottom: 0;
		width: 100%;
		.navigation {
			display: flex;
			border: solid 2rpx #f2f2f2;
			background-color: #ffffff;
			padding: 16rpx 0;
			justify-content: space-around;
			.left {
				display: flex;
				font-size: 20rpx;
				.item {
					margin: 0 30rpx;
					&.car {
						text-align: center;
						position: relative;
						.car-num {
							position: absolute;
							top: -10rpx;
							right: -10rpx;
						}
					}
				}
			}
			.right {
				width: 375rpx;
				display: flex;
				font-size: 28rpx;
				align-items: center;
				.btn {
					line-height: 50rpx;
					padding: 0 30rpx;
					border-radius: 36rpx;
					color: #ffffff;
				}
				.buy {
					background-color: #ff7900;
				}
				.buy-disabled {
					background-color: $u-type-info;
				}
			}
		}
	}
}
</style>
