<template>
	<view class="gg u-p-20">
		<!-- 利用.sync修饰符，将searchKeyword传递到子组件 -->
		<PickUpLocationHeader @getSearchLeader="getSearchLeader" :searchKeyword.sync="searchKeyword" />

		<u-gap height="20" />

		<map id="pickUpLocationMap" class="gg-location-map" show-location :scale="scale" :latitude="latitude" :longitude="longitude" :markers="covers" 			@markertap="mapMarkertap"></map>

		<view class="gg-location">
			<!-- 利用scroll-into-view进行选中提货点的定位操作 -->
			<scroll-view scroll-y @scrolltolower="loadMore" class="gg-location-scroll" :scroll-into-view="`map-${markerId}`" scroll-with-animation>
				<block v-if="searchResult.content.length > 0">
					<view class="u-m-b-20" v-for="(item, index) in searchResult.content" :key="item.id" :id="`map-${item.id}`">
						<PickUpLocationItem
							:location="item"
							:isCurrent="checkIsCurrent(item.id)"
							@selectPickUpLocation="selectPickUpLocation"
							:selected="markerId === item.id"
						></PickUpLocationItem>
					</view>
				</block>
				<u-empty mode="list" v-else></u-empty>
			</scroll-view>
		</view>

		<view class="gg-confirm-pick-up-location"><u-button type="warning">确认提货点</u-button></view>
	</view>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex';
export default {
	data() {
		return {
			searchKeyword: '',
			filter: {
				page: 1, // 当前页码
				limit: 5, // 每页记录数
				latitude: '', // 经度
				longitude: '' // 纬度
			},
			searchResult: {
				content: [],
				last: false
			},
			scale: 12, // 缩放尺寸
			markerId: 0, // 当前选中标记
			covers: [
				{
					latitude: 39.909,
					longitude: 116.39742,
					iconPath: '/static/images/location.png',
					width: 30,
					height: 30
				}
			]
		};
	},
	computed: {
		...mapState('pickUpLocationModule', ['leaderAddressVo']),
		...mapGetters('pickUpLocationModule', ['checkIsCurrent'])
	},
	methods: {
		...mapActions('pickUpLocationModule', ['selectLeaderAddressVoAction']),
		async getSearchLeader(data) {
			// 查询数据
			if (data) {
				Object.assign(this.$data.searchResult, this.$options.data().searchResult); // 这里重置 searchResult 下的所有数据
				Object.assign(this.$data.filter, this.$options.data().filter); // 这里重置 filter 下的所有数据
			}
			this.filter.latitude = data ? data.latitude : this.filter.latitude;
			this.filter.longitude = data ? data.longitude : this.filter.longitude;

			const o = {
				limit: this.filter.limit,
				page: this.filter.page,
				longitude: this.filter.longitude,
				latitude: this.filter.latitude
			};

			let result = await this.$u.api.getSearchLeader(o);
			this.searchResult = { ...result, content: [...this.searchResult.content, ...result.content] };
			
			// marker标记点的设置
			if (this.searchResult.content.length > 0) {
				let covers = [];
				this.searchResult.content.map(item => {
					covers.push({
						id:item.id,
						latitude: item.location.lat,
						longitude: item.location.lon,
						iconPath: '/static/images/location.png',
						width: 30,
						height: 30
					});
				});
			
				// 需要将查询结果地图上标识
				this.covers = covers;
				// 先将位置移动到第一个位置
				let latitude = this.searchResult.content[0].location.lat;
				let longitude = this.searchResult.content[0].location.lon;
				this.pickUpLocationMapCtx.moveToLocation({ latitude, longitude }); // 移动到该位置
				this.scale = 12;
			}
		},
		// 加载更多数据
		loadMore() {
			if (!this.searchResult.last) {
				this.filter.page = this.filter.page + 1;
				this.getSearchLeader();
			}
		},
		// 标记点点击操作
		mapMarkertap({detail:{markerId}}){
			this.markerId = markerId
		},
		// 选择提货点
		selectPickUpLocation(leaderId) {
			this.selectLeaderAddressVoAction({ leaderId });
		},
	},
	onReady: function(e) {
		this.pickUpLocationMapCtx = uni.createMapContext('pickUpLocationMap');
	},
	mounted() {
		uni.getLocation({
			type: 'gcj02',
			success: async res => {
				this.covers[0]['latitude'] = res.latitude;
				this.covers[0]['longitude'] = res.longitude;

				// 通过网页接口从经纬度获取地址信息
				// 会报：Unhandled promise rejection的错误，可以利用trycatch在error阶段获取数据
				try {
					const result = await this.$u.api.getBaiduMapAddress({ latitude: res.latitude, longitude: res.longitude });
				} catch (error) {
					this.searchKeyword = error.result.addressComponent.district + error.result.addressComponent.street;
				}

				this.pickUpLocationMapCtx.moveToLocation();
			}
		});
	}
};
</script>

<style lang="scss" scoped>
.gg {
	display: flex;
	flex-direction: column;

	/*  地图设置  */
	&-location-map {
		width: 100%;
		height: calc(100vh - 120rpx - 400rpx - 140rpx);
	}

	/* 滚动区域 */
	&-location {
		padding: 20rpx 20rpx 20rpx 20rpx;
		&-scroll {
			height: 400rpx;
		}
	}
}
</style>
