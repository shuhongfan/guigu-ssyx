<template>
	<view class="gg">
		<u-navbar :is-back="true" :border-bottom="false">
			<view class="u-flex u-m-l-20 u-m-r-20"><view class="u-font-xl u-m-r-20">订单详情</view></view>
		</u-navbar>
		<view class="gg-content">
			<scroll-view scroll-y="true" class="gg-cart-sv-container">
				<!-- 提货点 -->
				<u-card :padding="10" :show-head="true">
					<view slot="head" class="u-m-10">提货人联系方式</view>
					<view slot="body" class="u-m-10">
						<view class="u-p-20">收货人员：{{ order.receiverName }}</view>
						<view class="u-p-20">联系方式：{{ order.receiverPhone }}</view>
						<view class="u-p-20">提货地点：{{ order.receiverAddress }}</view>
					</view>
				</u-card>

				<!-- 商品 -->
				<u-card :padding="10" :show-head="true">
					<view slot="head" class="u-m-10">选购商品</view>
					<view slot="body" class="u-m-10">
						<view v-for="(orderItem, index) in order.orderItemList" :key="orderItem.id">
							<view class="u-body-item u-flex u-col-between u-p-10">
								<u-image :src="orderItem.imgUrl" :width="200" :height="200" :lazy-load="true"></u-image>
								<view class="orderItemPrices u-p-20">
									<view class="u-font-xl">{{ orderItem.skuName }}</view>
									<view>单价：￥{{ orderItem.skuPrice }}</view>
									<view>数量：{{ orderItem.skuNum }}</view>
									<view v-if="orderItem.splitActivityAmount > 0">活动金额:￥{{ orderItem.splitActivityAmount }}</view>
									<view v-if="orderItem.splitCouponAmount > 0">优惠券额:￥{{ orderItem.splitCouponAmount }}</view>
									<view>
										小计：
										<text class="u-type-error">￥{{ orderItem.splitTotalAmount }}</text>
									</view>
								</view>
							</view>
						</view>
						<view>
							<view class="u-flex u-row-between u-p-20">
								<view>商品总额：</view>
								<view>￥{{ order.originalTotalAmount }}</view>
							</view>
							<view class="u-flex u-row-between u-p-20">
								<view>实际付款：</view>
								<view>￥{{ order.totalAmount }}</view>
							</view>
						</view>
					</view>
				</u-card>

				<u-card :padding="10" :show-head="false">
					<view slot="body" class="u-m-10">
						<view class="u-flex u-row-between u-p-20">
							<view class="u-flex-1">订单编号：</view>
							<view class="u-flex">
								<view>{{ order.orderNo }}</view>
								<view class="u-type-primary u-p-l-10" @click="copyOrderNo">复制</view>
							</view>
						</view>
						<view class="u-flex u-row-between u-p-20">
							<view class="u-flex-1">下单时间：</view>
							<view>{{ order.createTime }}</view>
						</view>
					</view>
				</u-card>
			</scroll-view>

			<view class="gg-navigation">
				<view class="navigation"><view class="buy btn u-line-1" @click="payOrder">支付订单</view></view>
			</view>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			timer: null,
			resul: {},
			orderId: 0,
			order: {}
		};
	},
	onLoad(options) {
		this.orderId = +options.orderId;
	},
	methods: {
		async getOrderInfoById(orderId) {
			const result = await this.$u.api.getOrderInfo({ orderId });
			this.order = result;
		},
		copyOrderNo() {
			uni.setClipboardData({
				data: this.order.orderNo, //要被复制的内容
				success: () => {
					//复制成功的回调函数
					uni.showToast({
						title: '复制成功',
						icon: 'none'
					});
				}
			});
		},
		async payOrder() {
			const result = await this.$u.api.getWxPayment({ orderNo: this.order.orderNo });
			wx.requestPayment({
				timeStamp: result.timeStamp,
				nonceStr: result.nonceStr,
				package: result.package,
				signType: 'MD5',
				paySign: result.paySign,
				success: res => {
					this.$u.api.getOrderStatus({ orderNo: this.order.orderNo });
					uni.showToast({
						title: '支付成功',
						icon: 'none'
					});
					uni.switchTab({
						url:'/pages/index/index'
					});
				},
				fail: err => {
					uni.showToast({
						icon: 'none',
						title: err
					});
				}
			});
		}
	},
	mounted() {
		this.getOrderInfoById(this.orderId);
	}
};
</script>

<style lang="scss">
.gg {
	height: 100%;
	background-color: $u-bg-color;

	// scrollView的固定高度设置
	// 底部导航与顶部自定义导航高度需要减去
	&-cart-sv-container {
		height: calc(100vh - 90rpx - 136rpx);
	}

	.orderItemPrices {
		display: flex;
		flex-direction: column;
	}

	// 底部导航
	&-navigation {
		width: 100%;
		height: 90rpx;
		position: fixed;
		bottom: 0;
		.navigation {
			height: 100%;
			display: flex;
			justify-content: flex-end;
			border: solid 2rpx #f2f2f2;
			background-color: #ffffff;
			padding: 12rpx 0;
			.btn {
				line-height: 66rpx;
				padding: 0 30rpx;
				border-radius: 36rpx;
				color: #ffffff;
			}
			.buy {
				background-color: #ff7900;
				margin: 0 30rpx;
			}
		}
	}
}
</style>
