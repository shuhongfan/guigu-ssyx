<template>
	<view class="gg u-p-20">
		<PickUpLocationHeader @getSearchLeader="getSearchLeader" />
		<view class="gg-current-location-container u-m-t-20" v-if="leaderAddressVo">
			<PickUpLocationItem :location="leaderAddressVo" :isCurrent="checkIsCurrent(leaderAddressVo.leaderId)"></PickUpLocationItem>
		</view>

		<scroll-view scroll-y @scrolltolower="loadMore" class="gg-location-sv-container">
			<block v-if="searchResult.content.length > 0">
				<view class="u-m-b-20" v-for="(item, index) in searchResult.content" :key="item.id">
					<PickUpLocationItem :location="item" :isCurrent="checkIsCurrent(item.id)" @selectPickUpLocation="selectPickUpLocation"></PickUpLocationItem>
				</view>
			</block>
			<u-empty mode="list" v-else></u-empty>
		</scroll-view>
		
		<u-button @click="choosePickUpLocation" type="warning">搜索并选择其它提货点</u-button>
	</view>
</template>

<script>
import { mapState, mapGetters,mapActions } from 'vuex';
export default {
	data() {
		return {
			filter: {
				page: 1, // 当前页码
				limit: 5, // 每页记录数
				latitude: '', // 经度
				longitude: '' // 纬度
			},
			searchResult: {
				content: [], // 搜索的结果
				last: false // 是否已经最后
			}
		};
	},
	computed: {
		...mapState('pickUpLocationModule', ['leaderAddressVo']),
		...mapGetters('pickUpLocationModule', ['checkIsCurrent'])
	},
	methods: {
		...mapActions('pickUpLocationModule', ['selectLeaderAddressVoAction']),
		async getSearchLeader(data) {
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
		},
		// 加载更多数据
		loadMore() {
			if (!this.searchResult.last) {
				this.filter.page = this.filter.page + 1;
				this.getSearchLeader();
			}
		},
		// 选择提货点
		selectPickUpLocation(leaderId) {
			if (leaderId) this.selectLeaderAddressVoAction({ leaderId });
		},
		// 选择其它提货点
		choosePickUpLocation() {
			this.$u.route('/pagesLocation/choosePickUpLocation/choosePickUpLocation');
		},
	}
};
</script>

<style lang="scss" scoped>
.gg {
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	height: 100%;

	&-current-location-container {
		flex: 1;
	}

	&-location-sv-container {
		height: calc(100vh - 450rpx);
	}
}
</style>
