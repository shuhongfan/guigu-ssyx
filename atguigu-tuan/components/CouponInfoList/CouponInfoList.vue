<template>
	<u-popup mode="bottom" :closeable="true" border-radius="14" v-model="showCouponInfoListModal" @close="closeModal">
		<view class="coupon">
			<view class="content u-m-b-20" v-for="couponInfoItem in couponInfoList" :key="couponInfoItem.id">
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
					<block v-if="couponInfoItem.couponStatus === 1"><u-tag text="已领取" type="success" /></block>
					<block v-else-if="couponInfoItem.couponStatus === 2"><u-tag text="已使用" type="info" /></block>
					<block v-else><view size="mini" class="immediate-use" :round="true" @click="receiveCoupon(couponInfoItem.id)">领取</view></block>
				</view>
			</view>
		</view>
	</u-popup>
</template>

<script>
import dayjs from 'dayjs';
export default {
	props: ['showCouponInfoList', 'couponInfoList'],
	name: 'CouponInfoList',
	computed: {
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
		},
		showCouponInfoListModal: {
			get() {
				return this.showCouponInfoList;
			},
			set(value) {
				return false;
			}
		}
	},
	methods: {
		dayjs,
		closeModal() {
			this.$emit('update:showCouponInfoList', false);
		},
		// 领取优惠券
		receiveCoupon(id){
			this.$emit('getCouponInfo',id)
		}
	}
};
</script>

<style lang="scss">
.coupon {
	margin: 80rpx auto;
	margin-bottom: 30rpx;
	background-color: #ffffff;
	width: 700rpx;
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
			margin-left: 30rpx;
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
</style>
