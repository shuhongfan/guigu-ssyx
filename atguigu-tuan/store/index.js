import Vue from 'vue'
import Vuex from 'vuex'
import indexModule from './modules/index'
import pickUpLocationModule from './modules/pickUpLocation'
import categoriesModule from './modules/categories'
import cartModule from './modules/cart'
import orderModule from './modules/order'

Vue.use(Vuex)

export default new Vuex.Store({
	modules: {
		indexModule,
		pickUpLocationModule,
		categoriesModule,
		cartModule,
		orderModule
	},
})
