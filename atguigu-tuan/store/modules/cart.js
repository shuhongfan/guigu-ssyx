import Vue from "vue"
const state = {
	cartList: [],
	activityCartList: {}
};

const getters = {
	// 检查商品是否存在于购物车中
	checkProductExists(state) {
		return function(skuId) {
			const pos = state.cartList.findIndex(item => item.skuId === skuId)
			return pos === -1 ? false : true;
		}
	},
	// 获取单个商品的购买数量
	getProductSkuNum(state) {
		return function(skuId) {
			const index = state.cartList.findIndex(item => item.skuId === skuId);
			return index !== -1 ? state.cartList[index].skuNum : 0;
		}
	},
	// 获取购物车商品信息列表
	getCartInfoList(state) {
		return state.activityCartList.carInfoVoList
	},
	// 是否显示包含多个商品的内容
	showMultiCheckbox(state) {
		return function(index) {
			return state.activityCartList.carInfoVoList[index].cartInfoList.length > 1
		}
	},
	// 确认多个商品项是否为选中
	getMultiCheckCart(state) {
		return function(index) {
			return state.activityCartList.carInfoVoList[index].cartInfoList.every(item => item.isChecked === 1);
		}
	},
	// 获取多个商品项的ids
	getMultiCheckedIds(state) {
		return function(index) {
			let ids = []
			state.activityCartList.carInfoVoList[index].cartInfoList.forEach(item => ids.push(item.skuId));
			return ids.toString();
		}
	},
	// 判断是否全选
	isAllSelected(state) {
		let isAllSelected = true;
		state.activityCartList.carInfoVoList && state.activityCartList.carInfoVoList
			.forEach(carInfoItem => {
				carInfoItem.cartInfoList.forEach(cartInfoItem => {
					if (cartInfoItem.isChecked === 0) {
						isAllSelected = false;
						return false;
					}
				})
			})
		return isAllSelected;
	},
	// 获取购物车价格信息
	getCartPriceInfo(state) {
		if (!state.activityCartList.totalAmount) {
			return {
				couponReduceAmount: 0,
				originalTotalAmount: 0,
				totalAmount: 0
			}
		}
	
		return {
			couponReduceAmount: state.activityCartList.couponReduceAmount,
			originalTotalAmount: state.activityCartList.originalTotalAmount,
			totalAmount: state.activityCartList.totalAmount
		}
	},
	// 确认选中购物车的数量
	getSelectedCount(state) {
		let count = 0;
		state.activityCartList.carInfoVoList && state.activityCartList.carInfoVoList
			.forEach(carInfoItem => {
				carInfoItem.cartInfoList.forEach(cartInfoItem => {
					if (cartInfoItem.isChecked === 1) {
						count += cartInfoItem.skuNum;
					}
				})
			})
		return count;
	},
	// 获取购物车优惠券信息列表
	getCartCouponInfoList(state) {
		return state.activityCartList.couponInfoList
	},
}

const mutations = {
	// 添加到购物车
	addShopMutation(state, payload) {
		state.cartList.push(payload);
	},
	// 获取不带活动的购物车列表
	getCartListMutation(state, payload) {
		state.cartList = payload;
	},
	// 修改购物车数量
	changeSkuNumMutation(state, payload) {
		// skuId为商品id
		// value为+1或者-1，操作的递增值
		// currentBuyNum为number-box组件当前商品购物车的操作值
		const {
			skuId,
			value,
			currentBuyNum
		} = payload
		const index = state.cartList.findIndex(item => item.skuId === skuId);
		// 如果当前购买数量小于1则删除该商品
		if (currentBuyNum < 1) {
			state.cartList.splice(index, 1)
		} else {
			state.cartList[index].skuNum += value
		}
	},
	// 删除购物车
	deleteShopMutation(state, payload) {
		// 删除cartList中的数据
		const cartListIndex = state.cartList.findIndex(item => item.skuId === payload);
		state.cartList.splice(cartListIndex, 1)
	},
	// 获取带活动的购物车列表
	getActivityCartListMutation(state, payload) {
		state.activityCartList = payload
	},

}
const actions = {
	// 添加到购物车
	async addShopAction({
		commit,
		state
	}, payload) {
		// 给对象添加响应式数据属性
		Vue.set(payload, 'skuNum', 1)
		Vue.set(payload, 'skuId', payload.id)
		Vue.set(payload, 'isChecked', 1)
		await this._vm.$u.api.getAddToCart({
			skuId: payload.id,
			skuNum: payload.skuNum,
		})
		commit('addShopMutation', payload)
	},
	// 获取不带活动的购物车列表
	async getCartListAction({
		commit
	}) {
		let result = await this._vm.$u.api.getCartList()
		commit('getCartListMutation', result)
	},
	// 修改购物车数量
	async changeSkuNumAction({
		commit,
		dispatch
	}, payload) {
		const {
			skuId,
			value,
			currentBuyNum,
			isCart
		} = payload;
		// 如果当前购买的数量小于1，则需要将该商品从购物车中删除，否则进行购物车数量的修改
		if (currentBuyNum < 1) {
			dispatch('deleteShopAction', payload)
		} else {
			await this._vm.$u.api.getAddToCart({
				skuId: skuId,
				skuNum: value,
			})
			commit('changeSkuNumMutation', payload)
		}
		
		// 通过isCart判断是否是在购物车里进行购物车数量的改变，
		// 如果是在购物车里进行数量变化，则还需要获取带活动的购物车列表
		if (isCart) dispatch('getActivityCartListAction')

	},
	// 删除购物车
	async deleteShopAction({
		commit,
		dispatch
	}, payload) {
		const {
			skuId,
			value,
			currentBuyNum,
			isCart
		} = payload;
		await this._vm.$u.api.deleteCart(skuId);
		
		// 删除时如果是在购物车列表操作，则需要重新获取数据
		if (isCart) await dispatch('getActivityCartListAction')
		await commit('deleteShopMutation', skuId)
	},
	// 获取带活动的购物车列表
	async getActivityCartListAction({
		commit
	}, payload) {
		let showLoading = false;
		if (payload) showLoading = true

		let result = await this._vm.$u.api.getActivityCartList({
			showLoading
		})
		commit('getActivityCartListMutation', result)
	},
	// 切换购物车商品的选中状态
	async changeCheckCartAction({
		commit,
		dispatch
	}, payload) {
		let result = await this._vm.$u.api.getCheckCart(payload)
		dispatch('getActivityCartListAction')
	},
	// 对指定的多个商品进行选择/反选
	async changeMultiCheckedCartAction({
		commit,
		dispatch
	}, payload) {
		let result = await this._vm.$u.api.postBatchCheckCart(payload)
		dispatch('getActivityCartListAction')
	},
	// 对所有购物车商品进行全选/反选
	async changeAllCheckCartAction({
		commit,
		dispatch
	}, payload) {
		let result = await this._vm.$u.api.getCheckAllCart(payload)
		dispatch('getActivityCartListAction')
	},
};

export default {
	namespaced: true,
	state,
	mutations,
	actions,
	getters,
};
