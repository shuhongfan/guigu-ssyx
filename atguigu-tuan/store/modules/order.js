import Vue from 'vue'
const state = {
	order: {}
};

const getters = {
	// 获取订单商品信息列表
	getCartInfoList(state) {
		return state.order.carInfoVoList
	},
	// 获取提货点信息
	getLeaderAddressVo(state) {
		return state.order.leaderAddressVo
	},
	// 确认选中购物车的数量
	getSelectedCount(state) {
		let count = 0;
		state.order.carInfoVoList && state.order.carInfoVoList
			.forEach(carInfoItem => {
				carInfoItem.cartInfoList.forEach(cartInfoItem => {
					if (cartInfoItem.isChecked === 1) {
						count += cartInfoItem.skuNum;
					}
				})
			})
		return count;
	},
	// 获取购物车价格信息
	getCartPriceInfo(state) {
		if (!state.order.totalAmount) {
			return {
				couponReduceAmount: 0,
				originalTotalAmount: 0,
				totalAmount: 0,
				activityReduceAmount: 0
			}
		}
	
		return {
			couponReduceAmount: state.order.couponReduceAmount,
			originalTotalAmount: state.order.originalTotalAmount,
			totalAmount: state.order.totalAmount,
			activityReduceAmount: state.order.activityReduceAmount
		}
	},
	// 是否显示包含多个商品的内容
	showMultiCheckbox(state) {
		return function(index) {
			return state.order.carInfoVoList[index].cartInfoList.length > 1
		}
	},
	// 获取订单优惠券信息列表
	getCartCouponInfoList(state) {
		const couponInfoList = state.order.couponInfoList || []
		if (couponInfoList) {
			couponInfoList.forEach((
				item) => {
				Vue.set(item, 'selected', item.isOptimal === 1 && item.isSelect === 1)
	
			})
		}
		return couponInfoList
	},
}

const mutations = {
	// 获取确认订单
	getConfirmOrderMutation(state, payload) {
		state.order = payload
	}
}

const actions = {
	// 获取确认订单
	async getConfirmOrderAction({
		commit
	}) {
		let result = await this._vm.$u.api.getConfirmOrder()
		commit('getConfirmOrderMutation', result)
	}
}

export default {
	namespaced: true,
	state,
	mutations,
	actions,
	getters,
};
