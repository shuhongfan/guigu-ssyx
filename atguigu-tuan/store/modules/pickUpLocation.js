const state = {
	leaderAddressVo: {}, // 当前提货点
	currentPickUpArea: '', // 当前提货点区域
	findAllList: [], // 指定区域的提货点列表
};
const getters = {
	checkIsCurrent(state, payload) {
		return function(id) {
			if (state.leaderAddressVo) {
				return state.leaderAddressVo.leaderId === id;
			}else{
				return false
			}
		}
	},
};
const mutations = {
	getSysRegionFindAllListMutation(state, payload) {
		state.findAllList = payload
	},
	setCurrentPickUpAreaMutation(state,payload){
		state.currentPickUpArea = payload.regionName
	},
	changeCurrentPickUpAreaMutation(state, payload) {
		state.currentPickUpArea = payload[0].regionName
	},
	changeLeaderAddressVoMutation(state, payload) {
		state.leaderAddressVo = payload;
	}
};
const actions = {
	async getSysRegionFindAllListAction({
		commit
	}) {
		let result = await this._vm.$u.api.getSysRegionFindAllList();
		await commit('getSysRegionFindAllListMutation', result)
		await commit('changeCurrentPickUpAreaMutation', result)
	},
	changeLeaderAddressVoAction({
		commit,
		dispatch
	}, payload) {
		commit('changeLeaderAddressVoMutation', payload)
	},
	async selectLeaderAddressVoAction({
		commit,
		dispatch
	}, payload) {
		let result = await this._vm.$u.api.getSelectLeader(payload);
		// 从首页设置当前提货点
		dispatch('indexModule/getHomeIndexAction', {}, {
			root: true
		})
	}
};

export default {
	namespaced: true,
	state,
	mutations,
	actions,
	getters,
};
