<template>
	<view>
		<view class="wrap">
			<view class="u-tabs-box">
				<u-tabs-swiper activeColor="#f29100" ref="tabs" :list="list" :current="current" @change="change" :is-scroll="false" swiperWidth="750"></u-tabs-swiper>
			</view>
			<swiper class="swiper-box" :current="swiperCurrent">
				<swiper-item class="swiper-item" v-for="idx in 5" :key="idx">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view class="order" v-for="(res, index) in orderList[idx]" :key="res.id">
								<view class="top">
									<view class="left">
										[{{ index + 1 }}]
										<u-icon name="clock" :size="30" color="rgb(94,94,94)"></u-icon>
										<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
										<view class="store">{{ res.createTime }}</view>
									</view>
									<view class="right">{{ res.param.orderStatusName }}</view>
								</view>
								<view class="item" v-for="(item, index) in res.orderItemList" :key="index">
									<view class="left"><image :src="item.imgUrl" mode="aspectFill"></image></view>
									<view class="content">
										<view class="title u-line-2">{{ item.skuName }}</view>
										<view class="type">活动金额：￥{{ item.splitActivityAmount }}</view>
										<view class="type">优惠券金额：￥{{ item.splitCouponAmount }}</view>
									</view>
									<view class="right">
										<view class="price">￥{{ item.skuPrice }}</view>
										<view class="number">x{{ item.skuNum }}</view>
										<view class="u-type-error">￥{{ item.splitTotalAmount }}</view>
									</view>
								</view>
								<view class="total">
									<text class="total-price">
										<text class="decimal">现价：￥{{ res.totalAmount }} 原价：￥{{ res.originalTotalAmount }}</text>
									</text>
								</view>
								<view class="bottom">
									<view class="more"><u-icon name="more-dot-fill" color="rgb(203,203,203)"></u-icon></view>
									<view class="logistics btn">查看物流</view>
									<view class="exchange btn">卖了换钱</view>
									<view class="evaluate btn">评价</view>
								</view>
							</view>
							<u-gap height="30"></u-gap>
							<u-loadmore :status="loadStatus[idx]" bgColor="#f2f2f2"></u-loadmore>
						</view>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			// 订单列表
			orderList: [[], [], [], [], []],
			// 订单类型、状态与数量
			list: [
				{
					name: '待付款',
					orderStatus: 'UNPAID',
					count: 0
				},
				{
					name: '待发货',
					orderStatus: 'WAITING_DELEVER',
					count: 0
				},
				{
					name: '待收货',
					orderStatus: 'WAITING_TAKE',
					count: 0
				},
				{
					name: '待评价',
					orderStatus: 'WAITING_COMMON',
					count: 0
				},
				{
					name: '已完结',
					orderStatus: 'FINISHED',
					count: 0
				}
			],
			// 当前选中的选项卡
			current: 0,
			// 当前的swiper
			swiperCurrent: 0,
			// 每个选项卡数据加载的状态
			loadStatus: ['loadmore', 'loadmore', 'loadmore', 'loadmore', 'loadmore'],
			// 每个选项卡初始页面
			page: [1, 1, 1, 1, 1],
			// 每个选项卡总页数
			pages: [0, 0, 0, 0, 0],
			// 每次列表请求的记录条数
			limit: 5
		};
	},
	async onLoad(options) {
		// 请求不同状态的初始列表数据
		await this.getOrderList(0, 'UNPAID');
		await this.getOrderList(1, 'WAITING_DELEVER');
		await this.getOrderList(2, 'WAITING_TAKE');
		await this.getOrderList(3, 'WAITING_COMMON');
		await this.getOrderList(4, 'FINISHED');

		let current = +options.current;
		if (current !== -1) {
			this.change(current);
		}
	},

	methods: {
		// 触底加载
		reachBottom() {
			const orderStatus = this.list[this.current].orderStatus;
			if (this.loadStatus[this.current] !== 'nomore') this.getOrderList(this.current, orderStatus);
		},
		// 页面数据
		// idx是数组下标，orderStatus是订单状态的常量值，请求接口的时候需要使用
		async getOrderList(idx, orderStatus) {
			const result = await this.$u.api.getFindUserOrder({
				page: this.page[idx],
				limit: this.limit,
				orderStatus
			});

			// 给每个选项卡存储总页数以及总记录数
			this.pages[idx] = result.pages; // 设置总页数
			this.list[idx].count = result.total; // 总记录数

			// 对于每个选项卡
			// 其中每一个如果总页数为0，则没有记录，loadmore组件状态为nomore
			// 并且不需要执行后续操作内容
			if (this.pages[idx] === 0) {
				this.loadStatus.splice(idx, 1, 'nomore');
				return;
			}

			// 如果每个选项卡的当前页值小于总页数值，
			// 那么需要将请求后的数据与之前数据进行数据合并，
			// 并且确认loadmore组件的状态为loadmore可加载更多
			// 还需要将页码数进行+1操作
			if (this.page[idx] <= this.pages[idx]) {
				this.orderList[idx] = [...this.orderList[idx], ...result.records];
				this.loadStatus.splice(idx, 1, 'loadmore');
				this.page[idx] = this.page[idx] + 1;
			} else {
				this.loadStatus.splice(idx, 1, 'nomore');
			}
		},
		// tab栏切换
		change(index) {
			this.swiperCurrent = index;
			this.current = index;
			const orderStatus = this.list[index].orderStatus;
			this.getOrderList(index, orderStatus);
		}
	}
};
</script>

<style>
/* #ifndef H5 */
page {
	height: 100%;
	background-color: #f2f2f2;
}
/* #endif */
</style>

<style lang="scss" scoped>
.order {
	width: 710rpx;
	background-color: #ffffff;
	margin: 20rpx auto;
	border-radius: 20rpx;
	box-sizing: border-box;
	padding: 20rpx;
	font-size: 28rpx;
	.top {
		display: flex;
		justify-content: space-between;
		.left {
			display: flex;
			align-items: center;
			.store {
				margin: 0 10rpx;
				font-size: 32rpx;
				font-weight: bold;
			}
		}
		.right {
			color: $u-type-warning-dark;
		}
	}
	.item {
		display: flex;
		margin: 20rpx 0 0;
		.left {
			margin-right: 20rpx;
			image {
				width: 200rpx;
				height: 200rpx;
				border-radius: 10rpx;
			}
		}
		.content {
			flex: 1;

			.title {
				font-size: 28rpx;
				line-height: 50rpx;
			}
			.type {
				margin: 10rpx 0;
				font-size: 24rpx;
				color: $u-tips-color;
			}
			.delivery-time {
				color: #e5d001;
				font-size: 24rpx;
			}
		}
		.right {
			margin-left: 10rpx;
			padding-top: 20rpx;
			text-align: right;
			.decimal {
				font-size: 24rpx;
				margin-top: 4rpx;
			}
			.number {
				color: $u-tips-color;
				font-size: 24rpx;
			}
		}
	}
	.total {
		margin-top: 20rpx;
		text-align: right;
		font-size: 24rpx;
		.total-price {
			font-size: 32rpx;
		}
	}
	.bottom {
		display: flex;
		margin-top: 40rpx;
		padding: 0 10rpx;
		justify-content: space-between;
		align-items: center;
		.btn {
			line-height: 52rpx;
			width: 160rpx;
			border-radius: 26rpx;
			border: 2rpx solid $u-border-color;
			font-size: 26rpx;
			text-align: center;
			color: $u-type-info-dark;
		}
		.evaluate {
			color: $u-type-warning-dark;
			border-color: $u-type-warning-dark;
		}
	}
}
.centre {
	text-align: center;
	margin: 200rpx auto;
	font-size: 32rpx;
	image {
		width: 164rpx;
		height: 164rpx;
		border-radius: 50%;
		margin-bottom: 20rpx;
	}
	.tips {
		font-size: 24rpx;
		color: #999999;
		margin-top: 20rpx;
	}
	.btn {
		margin: 80rpx auto;
		width: 200rpx;
		border-radius: 32rpx;
		line-height: 64rpx;
		color: #ffffff;
		font-size: 26rpx;
		background: linear-gradient(270deg, rgba(249, 116, 90, 1) 0%, rgba(255, 158, 1, 1) 100%);
	}
}
.wrap {
	display: flex;
	flex-direction: column;
	height: calc(100vh - var(--window-top));
	width: 100%;
}
.swiper-box {
	flex: 1;
}
.swiper-item {
	height: 100%;
}
</style>
