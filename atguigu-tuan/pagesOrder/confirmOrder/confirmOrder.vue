<template>
	<view class="gg">
		<u-navbar :is-back="true" :border-bottom="false">
			<view class="u-flex u-m-l-20 u-m-r-20">
				<view class="u-font-xl u-m-r-20">生成订单</view>
				<view class="u-font-xs u-light-color u-p-t-10">共{{ getSelectedCount }}件商品</view>
			</view>
		</u-navbar>
		<view class="gg-content">
			<scroll-view scroll-y="true" class="gg-cart-sv-container" v-if="getCartInfoList">
				<!-- 提货点 -->
				<u-card :padding="10" :show-head="false">
					<view slot="body" class="u-m-10">提货点：{{ getLeaderAddressVo.takeName }}</view>
				</u-card>

				<u-card :padding="10" :show-head="true">
					<view slot="head" class="u-m-10">提货人联系方式</view>
					<view slot="body" class="u-m-10">
						<u-input v-model="sumbitOrderForm.receiverName"  placeholder="请输入提货人姓名" class="u-p-b-20" />
						<u-input v-model="sumbitOrderForm.receiverPhone"  placeholder="请输入提货人电话" type="number" />
					</view>
				</u-card>

				<!-- 商品 -->
				<view v-for="(cartInfoListItem, index) in getCartInfoList" :key="index">
					<u-card :padding="10" :border="false" :show-head="showMultiCheckbox(index)">
						<view slot="head" class="u-m-10">
							<text>{{ cartInfoListItem.activityRule.ruleDesc }}</text>
						</view>
						<view slot="body">
							<view v-for="(cartInfoItem, idx) in cartInfoListItem.cartInfoList" :key="idx">
								<!-- 动态class绑定是为了确保最后一条底部线条不显示 -->
								<view class="u-body-item u-flex u-col-between u-p-10" :class="{ 'u-border-bottom': idx !== cartInfoListItem.cartInfoList.length - 1 }">
									<ListImgItem
										:lazyLoad="false"
										:src="cartInfoItem.imgUrl"
										width="200rpx"
										height="200rpx"
										:showLeft="cartInfoItem.skuType === 1"
										:showRight="false"
										:showBottom="cartInfoItem.skuType === 0 && cartInfoItem.isNewPerson === 1"
									></ListImgItem>
									<view class="u-p-b-20 u-m-l-20" style="flex:1">
										<view>{{ cartInfoItem.skuName }}</view>
										<view>购买数量：{{ cartInfoItem.skuNum }}</view>
										<view class="u-flex u-row-between">
											<view class="u-type-error">
												<text>￥</text>
												<text class="u-font-xl">{{ cartInfoItem.cartPrice }}</text>
											</view>
										</view>
									</view>
								</view>
							</view>
						</view>
					</u-card>
				</view>

				<!-- 优惠券 -->
				<u-card :padding="10" :show-head="false" v-if="getCartCouponInfoList.length > 0">
					<view slot="body">
						<view class="coupon">
							<view
								class="content u-m-b-20"
								v-for="couponInfoItem in getCartCouponInfoList"
								:key="couponInfoItem.id"
								:class="{ selected: couponInfoItem.selected }"
								@click="switchCouponInfoItem(couponInfoItem)"
							>
								<view class="left">
									<view class="sum">
										￥
										<text class="num">{{ couponInfoItem.amount }}</text>
									</view>
									<view class="type">{{ couponInfoItem.couponType === 'FULL_REDUCTION' ? '满减券' : '现金券' }}</view>
									<view class="type">{{ getRangeType(couponInfoItem.rangeType) }}</view>
								</view>
								<view class="centre">
									<view class="title">{{ couponInfoItem.couponName }}</view>
									<view class="u-type-info">{{ couponInfoItem.rangeDesc }}</view>
									<view class="valid-date">过期时间：{{ dayjs(couponInfoItem.expireTime).format('YYYY-MM-DD') }}</view>
								</view>
								<view class="right">
									<block v-if="couponInfoItem.isSelect === 1"><u-tag text="可以使用" type="error" /></block>
									<block v-else><u-tag text="不能使用" type="info" /></block>
				
									<block v-if="couponInfoItem.isOptimal === 1"><u-tag text="最优推荐" type="error" /></block>
									<block v-else><u-tag text="非优使用" type="info" /></block>
								</view>
							</view>
						</view>
					</view>
				</u-card>
			</scroll-view>

			<view class="gg-navigation">
				<view class="navigation">
					<view class="left">
						<view class="item u-p-l-20">
							<view class="u-font-sm u-line-1">
								<view class="u-type-error">现价:￥{{ getCartPriceInfo.totalAmount }}</view>
								<view class="u-font-xs u-type-info">
									优惠券优惠:￥{{ getCartPriceInfo.couponReduceAmount }}
									<text class="u-font-xs u-m-l-20 u-type-info">原价:</text>
									<text class="u-font-xs u-type-info">￥{{ getCartPriceInfo.originalTotalAmount }}</text>
								</view>
							</view>
						</view>
					</view>
					<view class="right"><view class="buy btn u-line-1" @click="createSubmitOrder">生成订单</view></view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex';
import dayjs from 'dayjs';
export default {
	data() {
		return {
			couponId: 0,
			sumbitOrderForm: {
				couponId: 0,
				leaderId: 0,
				orderNo: '',
				receiverName: '',
				receiverPhone: ''
			}
		};
	},
	computed: {
		...mapState('orderModule', ['order']),
		...mapGetters('orderModule', ['getCartInfoList', 'getSelectedCount', 'getCartPriceInfo', 'getLeaderAddressVo', 'showMultiCheckbox','getCartCouponInfoList']),
		getRangeType() {
			return function(rangeType) {
				switch (rangeType) {
					case 'ALL':
						return '全场通用';
					case 'SKU':
						return '指定商品';
					case 'CATEGORY':
						return '指定分类';
				}
			};
		}
	},
	methods: {
		dayjs,
		...mapActions('orderModule', ['getConfirmOrderAction']),
		switchCouponInfoItem(couponInfoItem) {
			if (couponInfoItem.isSelect) {
				this.getCartCouponInfoList.forEach(item => (item.selected = false));
				couponInfoItem.selected = true;
		
				const couponReduceAmount = couponInfoItem.amount; // 优惠价格
				const totalAmount = this.getCartPriceInfo.originalTotalAmount - couponReduceAmount; // 总价
		
				this.getCartPriceInfo.totalAmount = totalAmount;
				this.getCartPriceInfo.couponReduceAmount = couponReduceAmount;
				this.couponId = couponInfoItem.id;
			} else {
				uni.showToast({
					title: '优惠券无法使用'
				});
			}
		},
		async createSubmitOrder() {
			this.sumbitOrderForm.orderNo = this.order.orderNo;
			this.sumbitOrderForm.couponId = this.couponId;
			this.sumbitOrderForm.leaderId = this.getLeaderAddressVo.leaderId;
		
			if (this.$u.test.isEmpty(this.sumbitOrderForm.receiverName)) {
				uni.showToast({
					title: '请输入提货者姓名',
					icon: 'none'
				});
				this.sumbitOrderForm.receiverName = '';
				return;
			}
		
			if (!this.$u.test.mobile(this.sumbitOrderForm.receiverPhone)) {
				uni.showToast({
					title: '请输入提货者手机号码',
					icon: 'none'
				});
				this.sumbitOrderForm.receiverPhone = '';
				return;
			}
		
			const result = await this.$u.api.postSubmitOrder(this.sumbitOrderForm);
			this.$u.route(`/pagesOrder/getOrderInfo/getOrderInfo?orderId=${result}`);
		}
	},
	mounted() {
		this.getConfirmOrderAction();
		
		this.getCartCouponInfoList.map(item => {
			if (item.selected) {
				this.couponId = item.id;
			}
		});
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

	// 底部导航
	&-navigation {
		width: 100%;
		height: 90rpx;
		position: fixed;
		bottom: 0;
		.navigation {
			height: 100%;
			display: flex;
			border: solid 2rpx #f2f2f2;
			background-color: #ffffff;
			padding: 12rpx 0;
			.left {
				display: flex;
				flex: 1;
				font-size: 20rpx;
				.item {
					margin: 0 10rpx;
					&.car {
						text-align: center;
						position: relative;
					}
				}
			}
			.right {
				display: flex;
				font-size: 28rpx;
				align-items: center;
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
}

.coupon {
	background-color: #ffffff;
	width: 100%;
	// border: 10rpx;
	color: $u-type-warning;
	font-size: 28rpx;
	.content {
		display: flex;
		justify-content: space-around;
		align-items: center;
		padding: 40rpx 20rpx;
		border: 10rpx;
		background-color: #fff5f4;
		.left {
			.sum {
				font-size: 32rpx;
				.num {
					font-size: 60rpx;
					font-weight: bold;
				}
			}
		}
		.centre {
			margin-left: 40rpx;
			.title {
				font-size: 32rpx;
				font-weight: bold;
				color: $u-main-color;
			}
		}
		.right {
			margin-left: 10rpx;
			display: flex;
			flex-direction: column;
			align-items: center;
			u-tag {
				padding: 5rpx;
			}
			.immediate-use {
				padding: 0 20rpx;
				height: 50rpx;
				border-radius: 25rpx;
				line-height: 50rpx;
				background-color: $u-type-warning !important;
				color: #ffffff !important;
				font-size: 24rpx;
				border: none;
				word-break: keep-all;
			}
		}
	}
}

.selected {
	background-color: $u-type-error !important;
}
</style>
