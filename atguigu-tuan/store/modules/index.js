const state = {
	home: {}
};
const getters = {
	// 商品分类
	categoryList(state) {
		return state.home.categoryList || [];
	},
	// 热销商品
	hotSkuList(state) {
		return state.home.hotSkuList || [];
	},
	// 新人专享
	newPersonSkuInfoList(state) {
		return state.home.newPersonSkuInfoList || [];
	},
	// 提货点信息
	leaderAddressVo(state) {
		return state.home.leaderAddressVo || {}
	},
	// 秒杀时间
	seckillTime(state) {
		return state.home.seckillTime || {}
	},
	// 秒杀商品
	seckillSkuVoList(state) {
		return state.home.seckillSkuVoList || []
	}
};
const mutations = {
	// 获取首页数据
	getHomeIndexMutation(state, payload) {
		state.home = payload
	}
};
const actions = {
	async getHomeIndexAction({
		commit,
		dispatch
	}) {
		// 直接用this.$u.api在仓库中是无法调用到对应的接口的，因为this对象指向不同
		// 仓库中的this指向的是Store，所以需要通过this._vm来找到对应的Vue实例
		let result = await this._vm.$u.api.getHomeIndex()
		await commit('getHomeIndexMutation', result)
		// 利用root属性将派发pickUpLocation模块中的action动作
		dispatch('pickUpLocationModule/changeLeaderAddressVoAction', result.leaderAddressVo, {
			root: true
		})
		
		dispatch('cartModule/getCartListAction',{},{root:true})
	}
};
export default {
	namespaced: true,
	state,
	mutations,
	actions,
	getters,
};
