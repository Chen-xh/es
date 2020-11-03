// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false;
import axios from 'axios';
import VueAxios from 'vue-axios'
// element-ui 引入文件
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
//注册 VueAxios, axios
Vue.use(VueAxios, axios);
Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
