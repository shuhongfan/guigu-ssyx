<template>
	<view class="gg">
		<u-navbar :is-back="false" :border-bottom="false" :background="{ 'background-image': 'linear-gradient(to right, rgb(255,180,61), rgb(255, 101, 0))' }"></u-navbar>
		<view class="gg-content">
			<view class="gg-header u-p-l-20 u-p-r-20">
				<view class="u-flex u-m-l-20">
					<u-image :src="userInfo.photoUrl" shape="circle" width="100rpx" height="100rpx" />
					<view class="u-font-lg u-type-info-light u-m-l-20">Hi {{ userInfo.nickName }}</view>
				</view>

				<view class="wrap u-p-t-20" justify="between">
					<u-row gutter="16">
						<u-col span="4">
							<view class="gg-order-item">
								<view class="u-font-lg u-type-info-light">0元</view>
								<view class="u-font-xs u-type-info-light">资产</view>
							</view>
						</u-col>
						<u-col span="4">
							<view class="gg-order-item">
								<view class="u-font-lg u-type-info-light">0.00元</view>
								<view class="u-font-xs u-type-info-light">红包</view>
							</view>
						</u-col>
						<u-col span="4">
							<view class="gg-order-item">
								<view class="u-font-lg u-type-info-light">0张</view>
								<view class="u-font-xs u-type-info-light">优惠券</view>
							</view>
						</u-col>
					</u-row>
				</view>
			</view>
			<view class="gg-header-bottom"></view>

			<view class="gg-content-container">
				<view class="gg-content-container-box">
					<view class="u-flex u-row-between">
						<view class="u-m-20">我的订单</view>
						<view class="u-m-20 u-font-xs u-type-info" @click="gotoOrderList(-1)">查看全部 ></view>
					</view>
					<view class="u-flex u-row-around">
						<view class="gg-order-item"  @click="gotoOrderList(0)">
							<view class="iconfont icon-daifukuan u-type-warning"></view>
							<view class="u-font-xs">待付款</view>
						</view>

						<view class="gg-order-item"  @click="gotoOrderList(1)">
							<view class="iconfont icon-daifahuo1 u-type-warning"></view>
							<view class="u-font-xs u-p-l-5">待发货</view>
						</view>

						<view class="gg-order-item"  @click="gotoOrderList(2)">
							<view class="iconfont icon-tihuoguanli  u-type-warning"></view>
							<view class="u-font-xs u-p-l-5">待提货</view>
						</view>

						<view class="gg-order-item"  @click="gotoOrderList(3)">
							<view class="iconfont icon-pinglun2 u-type-warning"></view>
							<view class="u-font-xs u-p-l-10">待评价</view>
						</view>

						<view class="gg-order-item"  @click="gotoOrderList(4)">
							<view class="iconfont icon-tuikuanshouhou u-type-warning"></view>
							<view class="u-font-xs">已完结</view>
						</view>
					</view>
					<view class="u-p-20"></view>
				</view>

				<view class="gg-content-container-box  u-m-t-20">
					<view class="u-flex u-row-around u-p-t-20">
						<view class="gg-order-item">
							<image :src="recommend" class="gg-svg-icon"></image>
							<view class="u-font-xs">有奖推荐</view>
						</view>

						<view class="gg-order-item">
							<image :src="invite" class="gg-svg-icon"></image>
							<view class="u-font-xs u-p-l-5">邀新团长有奖</view>
						</view>

						<view class="gg-order-item">
							<image :src="fruit" class="gg-svg-icon"></image>
							<view class="u-font-xs u-p-l-5">7天种水果</view>
						</view>

						<view class="gg-order-item">
							<image :src="discount" class="gg-svg-icon"></image>
							<view class="u-font-xs u-p-l-10">天天低价</view>
						</view>
					</view>
					<view class="u-p-20"></view>
				</view>

				<view class="gg-content-container-box  u-m-t-20"><u-swiper :list="list"></u-swiper></view>

				<view class="gg-content-container-box  u-m-t-20">
					<view class="wrap u-p-t-20" justify="between">
						<u-row gutter="16">
							<u-col span="3">
								<view class="gg-order-item">
									<view class="iconfont icon-ico"></view>
									<view class="u-font-xs">提货码</view>
								</view>
							</u-col>
							<u-col span="3">
								<view class="gg-order-item" @click="gotoMyPickUpLocation">
									<view class="iconfont icon-dianpu"></view>
									<view class="u-font-xs">提货点管理</view>
								</view>
							</u-col>
							<u-col span="3">
								<view class="gg-order-item">
									<view class="iconfont icon-miaosha"></view>
									<view class="u-font-xs">秒杀提醒</view>
								</view>
							</u-col>
							<u-col span="3">
								<view class="gg-order-item">
									<view class="iconfont icon-yonghu"></view>
									<view class="u-font-xs">成为团长</view>
								</view>
							</u-col>
						</u-row>
						<u-gap height="30"></u-gap>
						<u-row gutter="16">
							<u-col span="3">
								<view class="gg-order-item">
									<view class="iconfont icon-kefu"></view>
									<view class="u-font-xs">联系客服</view>
								</view>
							</u-col>
							<u-col span="3">
								<view class="gg-order-item">
									<view class="iconfont icon-shanghupiliangruzhu"></view>
									<view class="u-font-xs">商户入驻</view>
								</view>
							</u-col>
							<u-col span="3">
								<view class="gg-order-item">
									<view class="iconfont icon-jiameng"></view>
									<view class="u-font-xs">代理商加盟</view>
								</view>
							</u-col>
							<u-col span="3">
								<view class="gg-order-item">
									<view class="iconfont icon-tousu"></view>
									<view class="u-font-xs">投诉与建议</view>
								</view>
							</u-col>
						</u-row>
						<u-gap height="30"></u-gap>
						<u-row gutter="16">
							<u-col span="3">
								<view class="gg-order-item">
									<view class="iconfont icon-shezhi"></view>
									<view class="u-font-xs">设置</view>
								</view>
							</u-col>
						</u-row>
						<u-gap height="30"></u-gap>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
// https://www.zhangxinxu.com/sp/svgo/ 利用在线svg转base64功能
// 将svg图形转成base64，然后再设置成js变量的形式引入使用
// 需要注意将变量设置到data中
// 强调：为什么不用iconfont呢？和上面的iconfont一样。因为彩色图标变黑了，色彩没起作用。
import { fruit, recommend, invite, discount } from '@/common/svgIcon.js';
export default {
	data() {
		return {
			userInfo: {},
			fruit,
			recommend,
			invite,
			discount,
			list: [
				{
					image: 'https://cdn.uviewui.com/uview/swiper/1.jpg',
					title: '昨夜星辰昨夜风，画楼西畔桂堂东'
				},
				{
					image: 'https://cdn.uviewui.com/uview/swiper/2.jpg',
					title: '身无彩凤双飞翼，心有灵犀一点通'
				},
				{
					image: 'https://cdn.uviewui.com/uview/swiper/3.jpg',
					title: '谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳'
				}
			]
		};
	},
	methods: {
		gotoOrderList(index) {
			this.$u.route('/pagesOrder/orderList/orderList?current=' + index);
		},
		gotoMyPickUpLocation(){
			uni.navigateTo({
				url: '/pagesLocation/myPickUpLocation/myPickUpLocation'
			});
		}
	},
	async mounted() {
		this.userInfo = uni.getStorageSync('userInfo');
	}
};
</script>

<style lang="scss" scoped>
.gg {
	height: 100%;
	&-content {
		height: 100%;
		background-color: $u-bg-color;
	}

	&-header {
		background-image: linear-gradient(to right, rgb(255, 180, 61), rgb(255, 101, 0));
		height: 300rpx;
	}

	// 头部区域底部左右圆角区域
	&-header-bottom {
		background-image: linear-gradient(to right, rgb(255, 180, 61), rgb(255, 101, 0));
		height: 100rpx;
		border-bottom-left-radius: 100rpx;
		border-bottom-right-radius: 100rpx;
	}

	&-content-container {
		margin-left: 20rpx;
		width: 710rpx;
		position: absolute;
		top: 370rpx;
		&-box {
			display: flex;
			flex-direction: column;
			background-color: #fff;
			border-radius: 25rpx;
			.iconfont {
				font-size: 50rpx;
				width: 80rpx;
				height: 60rpx;
				text-align: center;
			}
		}
	}

	&-order-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	&-svg-icon {
		width: 80rpx;
		height: 80rpx;
	}
}
</style>
