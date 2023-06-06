<template>
	<view>
		<!-- 如果商品没有加入购物车 -->
		<block v-if="getCheckProductExists"><u-button type="warning" size="mini" @click="addShopCart">加入购物车</u-button></block>
		<block v-else>
			<u-number-box
				ref="uNumber"
				:size="20"
				:input-width="60"
				:input-height="40"
				:disabled-input="true"
				@minus="changeSkuNumMinus"
				@plus="changeSkuNumPlus"
				v-model="skuNumValue"
				:min="0"
				:max="getMax"
			></u-number-box>
		</block>
		
		<u-modal v-model="showConfirmModal" @confirm="modalConfirm" @cancel="modalCancel" :show-cancel-button="true" ref="confirmModal" :async-close="true">
			<view class="u-flex u-row-center u-m-20">确认删除该商品？</view>
		</u-modal>
	</view>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
export default {
	name: 'AddToCart',
	data() {
		return {
			id: 0,
			isCart: false,
			showConfirmModal: false
		};
	},
	props: {
		shopDetail: {
			type: Object,
			default: () => {
				return {};
			}
		},
		// 在购物车页面的时候，使用带活动的购物车将传递的不再是商品的id
		// 商品详情信息中没有id，而是skuId
		skuId: {
			type: Number,
			default: null
		}
	},
	computed: {
		...mapGetters('cartModule', ['checkProductExists','getProductSkuNum']),
		// 检查是否存在商品于购物车
		getCheckProductExists() {
			return !this.checkProductExists(this.id);
		},
		// 商品加入购物车的数量
		skuNumValue: {
			get() {
				return this.getProductSkuNum(this.id);
			},
			set(value) {
			}
		},
		// 最大可加入购物车的数量
		getMax(){
			let perLimit = this.shopDetail.perLimit;
			// 秒杀商品与普通商品购买上限判断字段不同
			if(this.shopDetail.skuType ===1){
				perLimit = this.shopDetail.seckillSkuVo && this.shopDetail.seckillSkuVo.seckillLimit
			}
			return perLimit;
		}
	},
	methods: {
		...mapActions('cartModule', ['addShopAction', 'changeSkuNumAction']),
		// 添加到购物车
		addShopCart() {
			if (this.skuId) {
				this.shopDetail.id = this.skuId;
			}
			this.addShopAction(this.shopDetail);
		},
		// 购物车数量递减
		changeSkuNumMinus(e) {
			// 如果是购物车中进行递减操作，并且购物车数量为0，那么需要弹出确认框
			if (e.value < 1 && this.isCart) {
				this.showConfirmModal = true;
			} else {
				this.changeSkuNumAction({ skuId: this.id, value: -1, currentBuyNum: e.value, isCart: this.isCart });
			}
			
		},
		// 购物车数量递增
		changeSkuNumPlus(e) {
			this.changeSkuNumAction({ skuId: this.id, value: 1, currentBuyNum: e.value,isCart: this.isCart });
		},
		// 确认删除商品模态框
		modalConfirm() {
			this.showConfirmModal = false;
			this.changeSkuNumAction({ skuId: this.id, value: -1, currentBuyNum: 0, isCart: this.isCart });
		},
		// 取消删除商品模态框，只需要将商品购买数修改回初始值
		modalCancel() {
			this.showConfirmModal = false;
			// 重新设置u-number-box的初始值
			this.$refs.uNumber.$data.inputVal = 1;
		},
	},
	watch: {
		// skuId是在购物车频道使用，如果是商品列表，是id
		skuId: {
			handler(newVal, oldVal) {
				if (newVal) {
					this.id = newVal;
					this.isCart = true;
				} else {
					this.id = this.shopDetail.id;
					this.isCart = false;
				}
			},
			immediate: true
		}
	}
};
</script>

<style lang="scss"></style>
