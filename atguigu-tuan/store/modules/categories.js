const state = {
	categories: []
};
const getters = {
	
};
const mutations = {
	updateCategoriesMutation(state,payload){
		state.categories = payload;
	}
};
const actions = {
	async getCategoriesAction({
		commit
	}) {
		return new Promise(async reslove=>{
			let result = await this._vm.$u.api.getCategories()
			commit('updateCategoriesMutation',result)
			reslove();
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
