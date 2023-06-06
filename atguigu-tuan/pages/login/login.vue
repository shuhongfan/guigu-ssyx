<template>
	<view class="wrap">
		<view class="content"><view class="title">欢迎登陆硅谷团购平台</view></view>
		<view class="buttom">
			<view class="loginType">
				<!-- 对于按钮需要进行禁用判断，以防止多次点击 -->
				<button @click="getUserMsg" class="loginBtn" :disabled="isLogin">
					<view class="wechat item">
						<view class="icon"><u-icon size="200" name="weixin-fill" color="rgb(83,194,64)"></u-icon></view>
						微信登陆
					</view>
				</button>
			</view>
			<view class="hint">
				登录代表同意
				<text class="link">硅谷团购平台用户协议、隐私政策，</text>
				并授权使用您的硅谷团购平台账号信息（如昵称、头像、收获地址）以便您统一管理
			</view>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			// 防止按钮多次点击的布尔值
			isLogin:false, 
		};
	},
	methods: {
		// 获取用户信息
		getUserMsg() {
			// 不允许按钮点击
			this.isLogin = true;
			// 获取用户信息
			uni.getUserProfile({
				desc: '获取用户信息',
				success: res => {
					// 获取到用户头像、昵称、性别等内容，并将它们进行本地缓存的存储
					const photoUrl = res.userInfo.avatarUrl;
					const nickName = res.userInfo.nickName;
					const sex = res.userInfo.gender;
					uni.setStorageSync('userInfo', { photoUrl, nickName, sex });
					// 进行微信登陆操作（具体查看小程序登陆wx.login流程图）
					// https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
					uni.login({
						success: async res => {
							// 利用小程序登陆，配合code码进行用户登陆操作，并返回用户的token值
							const result = await this.$u.api.getWxLogin({ code: res.code });
							console.log(result,result.token,"<<<")
							// 将token值进行本地缓存存储
							uni.setStorage({
								key: 'token',
								data: result.token,
								success: async () => {
									// 更新用户信息
									await this.$u.api.postUpdateUser({
										sex,
										photoUrl,
										nickName
									});
									// 路由跳转至首页
									uni.reLaunch({
										url: '/pages/index/index',
									});
								}
							});
						}
					});
				},
				fail: err => {
					console.log(err);
				},
				complete: () => {
					// 恢复按钮可用性
					this.isLogin = false;
				}
			});
		}
	}
};
</script>

<style lang="scss" scoped>
.wrap {
	font-size: 28rpx;
	.content {
		width: 600rpx;
		margin: 80rpx auto 0;
		.title {
			text-align: left;
			font-size: 60rpx;
			font-weight: 500;
			margin-bottom: 100rpx;
		}
	}
	.buttom {
		.loginType {
			display: flex;
			justify-content: center;
			padding: 100rpx;
			.loginBtn {
				background: transparent;
			}
			.loginBtn::after {
				border: none;
			}
			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				color: $u-content-color;
				font-size: 28rpx;
			}
		}
		.hint {
			padding: 20rpx 40rpx;
			font-size: 24rpx;
			color: $u-tips-color;
			.link {
				color: $u-type-warning;
			}
		}
	}
}
</style>
