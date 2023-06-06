<template>
	<block>
		<u-tag v-if="isCurrent" class="gg-current-location-tag u-m-l-20" mode="dark" text="当前提货点" type="error" size="mini" />

		<view class="gg-current-location u-p-20" @click="changePickUpLocation(location.id)" :class="{ selected }">
			<u-image class="gg-current-location-img" width="150rpx" height="150rpx" border-radius="10rpx" :src="location.storePath"></u-image>
			<view class="gg-current-location-msg u-m-l-20 u-m-r-20">
				<view class="gg-current-location-msg-title u-m-b-5">{{ location.takeName }}</view>
				<view class="gg-current-location-msg-distance-address u-m-t-5">
					<text class="gg-current-location-msg-distance u-font-xs u-m-r-20" v-if="location.distance">距离最近*距离{{ location.distance }}km</text>
					<text class="gg-current-location-msg-address u-font-xs">{{ location.detailAddress }}</text>
				</view>
			</view>
			<block v-if="showRadio">
				<view class="gg-current-location-btn" v-if="isCurrent"><u-icon name="checkmark-circle-fill" color="#fa3534" size="40" /></view>
				<view class="gg-current-location-btn" v-else><u-icon name="checkmark-circle-fill" size="40" /></view>
			</block>
		</view>
	</block>
</template>

<script>
export default {
	name: 'PickUpLocationItem',
	props: {
		selected: {
			type: Boolean,
			default: false
		},
		isCurrent: {
			type: Boolean,
			default: true
		},
		showRadio: {
			type: Boolean,
			default: true
		},
		location: {
			type: Object,
			default: () => {
				return {};
			}
		}
	},
	methods: {
		changePickUpLocation(id) {
			this.$emit('selectPickUpLocation', id);
		}
	}
};
</script>

<style lang="scss" scoped>
.selected {
	background-color: $u-type-info !important;
}
.gg-current-location {
	display: flex;
	background-color: $u-type-info-light;
	border-radius: 20rpx;

	&-tag {
		position: absolute;
		z-index: 9999;
	}

	&-msg {
		flex: 1;

		&-title {
			font-weight: bold;
		}

		&-distance {
			color: $u-type-error;
		}

		&-address {
			color: $u-type-info;
		}
	}

	&-btn {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
}
</style>
