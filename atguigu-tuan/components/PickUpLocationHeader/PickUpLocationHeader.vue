<template>
	<view class="gg-header">
		<view class="gg-header-location u-p-r-50" @click="show = true">
			<text class="u-p-r-10">{{ currentPickUpArea }}</text>
			<u-icon name="arrow-down-fill" color="gray" size="20"></u-icon>
		</view>
		<!-- 如果利用v-model操作，会造成searchKeyword对于父组件的二次重渲染问题，所以将value与blur拆分，
		并且利用update:searchKeyword的方式将数据传递到父组件，并进行本组件的数据搜索操作 -->
		<u-search
			:value="searchKeyword"
			@blur="onBlurSearch"
			class="gg-header-search"
			input-align="left"
			height="70"
			:clearabled="false"
			:show-action="true"
			@search="searchLocation"
			@custom="searchLocation"
		></u-search>
		<u-picker mode="selector" v-model="show" :default-selector="[0]" :range="findAllList" range-key="regionName" @confirm="findAllListConfirm"></u-picker>
	</view>
</template>

<script>
import { mapState,mapMutations, mapActions } from 'vuex';
import { BAIDU_MAP_AK } from '../../common/const.js';
// 百度地址资料：https://lbsyun.baidu.com/index.php?title=wxjsapi/guide/geocoding
const bmap = require('../../common/bmap-wx.min.js');
const BMap = new bmap.BMapWX({
	ak: BAIDU_MAP_AK
});

export default {
	name: 'PickUpLocationHeader',
	props: ['searchKeyword'],
	data() {
		return {
			show: false,
			latitude: '',
			longitude: ''
		};
	},
	computed: {
		...mapState('pickUpLocationModule', ['findAllList', 'currentPickUpArea'])
	},
	methods: {
		...mapMutations('pickUpLocationModule',['setCurrentPickUpAreaMutation']),
		...mapActions('pickUpLocationModule', ['getSysRegionFindAllListAction']),
		findAllListConfirm(index) {
			// 通过百度地图进行地址解析，返回经纬度，并通知父组件进行列表显示处理
			// 发起geocoding检索请求
			BMap.geocoding({
				address: this.findAllList[index],
				fail: e => {
					console.log(e);
				},
				success: data => {
					let wxMarkerData = data.wxMarkerData;
					this.latitude = wxMarkerData[0].latitude;
					this.longitude = wxMarkerData[0].longitude;
					// 通过父组件查询数据
					this.$emit('getSearchLeader', {
						latitude: this.latitude,
						longitude: this.longitude
					});
					
					this.setCurrentPickUpAreaMutation(this.findAllList[index])
				}
			});
		},
		searchLocation(value) {
			if (value && value.trim().length > 0) {
				BMap.geocoding({
					address: value,
					fail: e => {
						console.log(e);
					},
					success: data => {
						let wxMarkerData = data.wxMarkerData;
						this.latitude = wxMarkerData[0].latitude;
						this.longitude = wxMarkerData[0].longitude;

						// 通过父组件查询数据
						this.$emit('getSearchLeader', {
							latitude: this.latitude,
							longitude: this.longitude
						});
					}
				});
			}
		},
		onBlurSearch(value) {
			this.$emit('update:searchKeyword', value);
		}
	},
	watch: {
		searchKeyword: {
			handler(newVal, oldVal) {
				console.log(newVal,oldVal)
				this.searchLocation(newVal);
			},
			immediate: true
		}
	},
	mounted() {
		this.getSysRegionFindAllListAction();
	}
};
</script>

<style lang="scss" scoped>
.gg {
	&-header {
		display: flex;
		justify-content: center;
		align-items: center;

		&-location {
			text {
				color: gray;
			}
		}

		&-search {
			flex: 1;
		}
	}
}
</style>
