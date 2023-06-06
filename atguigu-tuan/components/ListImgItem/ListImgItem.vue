<template>
	<view class="gg" :style="[{ width: width, height: height }]">
		<view class="gg-left u-font-xs u-p-l-10 u-p-r-10">
			<block v-if="showLeft"><slot name="left">秒杀</slot></block>
		</view>
		<view class="gg-right u-font-xs u-p-l-10 u-p-r-10">
			<block v-if="showRight"><slot name="right">推荐</slot></block>
		</view>
		<u-image :src="src" :width="width" :height="height" :lazy-load="lazyLoad" :border-radius="borderRadius" />
		<view class="gg-bottom u-font-xs u-p-l-10 u-p-r-10">
			<block v-if="showBottom"><slot name="bottom">新人专享</slot></block>
		</view>
	</view>
</template>

<script>
export default {
	name: 'ListImgItem',
	props: {
		lazyLoad: {
			type: Boolean,
			default: true
		},
		borderRadius: {
			type: String,
			default: '10rpx'
		},
		src: {
			type: String,
			default: '',
			required: true
		},
		width: {
			type: String,
			default: '100%'
		},
		height: {
			type: String,
			default: '200rpx'
		},
		showLeft: {
			type: Boolean,
			default: true
		},
		showRight: {
			type: Boolean,
			default: true
		},
		showBottom: {
			type: Boolean,
			default: true
		}
	}
};
</script>

<style lang="scss" scoped>
.gg {
	position: relative;

	&-left {
		position: absolute;
		top: 0;
		left: 0;
		z-index: 1;
		background-color: $u-type-primary;
		color: $u-type-info-light;
		border-top-left-radius: 10rpx;
	}
	&-right {
		position: absolute;
		right: 0;
		top: 0;
		z-index: 1;
		background-color: $u-type-error;
		color: $u-type-info-light;
		border-top-right-radius: 10rpx;
	}

	&-bottom {
		position: absolute;
		bottom: 0;
		left: 0;
		z-index: 1;
		width: 100%;
		background-color: $u-type-success;
		color: $u-type-info-light;
		border-bottom-left-radius: 10rpx;
		border-bottom-right-radius: 10rpx;
	}
}
</style>
