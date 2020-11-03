<template>
  <div>
    <!-- 上方搜索框，和下拉即时搜索 -->
    <el-autocomplete
      v-model="state"
      :fetch-suggestions="querySearchAsync"
      placeholder="请输入内容"
      @select="handleSelect"
      select-when-unmatched="true"
      :debounce="0"
    ></el-autocomplete>
    <el-button slot="append" icon="el-icon-search" @click="onSubmit"></el-button>

    <!-- 下方搜索结果显示 -->
    <div class="div2" v-show="con">
      <p style="font-size:22px">
        搜索
        <span style="color:	#F08080">{{state}}</span>的结果（总共搜索到
        <span style="color:	#F08080">{{total}}</span>条记录）
      </p>
      <p v-for="entity in All" class="p2">
        <a :href=entity.url v-html=entity.titleLight></a>
      </p>
      <!-- 分页组件 -->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="15"
        @current-change="handleCurrentChange"
        :current-page="page"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
    //导入axios，进行ajax访问
    const axios = require("axios");
    export default {
        data() {
            return {
                con: false,//控制下方显示框，隐藏或显示
                restaurants: [],//即时搜索，下拉框信息
                state: '',//搜索条件
                timeout: null,
                All: [],//下方显示框数据集合
                total: 0,  //搜索返回的总结果数量
                page: 1,//分页的当前页数
            };
        },
        watch: {
            state: { // 监视字段，页数
                handler() {
                    if (this.state.length > 0) {//如果有搜索条件
                        this.restaurants = [];//先清空即时搜索集合数据
                        this.loadAll();//查询
                    } else {
                        this.con = false;//没有搜索条件，隐藏下方数据显示窗口
                        this.restaurants = [];
                        this.All = [];//并清空结果集合
                        this.page = 1;//分页归一
                    }
                }
            },
            page: { // 监视字段，页数
                handler() {
                    this.loadAll();//如果页面发生变化，就查询新的页面的数据
                }
            }
        },
        methods: {
            //分页是上下页，触发的方法
            handleCurrentChange(val) {//当前页
                this.page = val;
                console.log(`当前页: ${val}`);
            },

            loadAll() {
                var app = this;
                axios.get("http://localhost:8888/search/", {
                    params: {
                        'key': app.state,//搜索条件
                        'page': app.page//当前第几页
                    }
                }).then(function (resp) {
                    console.info(resp.data);
                    app.total = resp.data.total;//当前数据一共有多少条
                    var rs = resp.data.items;
                    app.All = rs;//给显示结果的集合赋值
                    if (rs.length > 0) {
                        for (var i = 0; i < 10; i++) {//只显示10条提示
                            app.restaurants[i] = {value: rs[i].title, cid: rs[i].id}//给及时搜索下拉框赋值
                        }
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            },
            querySearchAsync(queryString, cb) {//加载即时搜索条件
                var results = this.restaurants;
                clearTimeout(this.timeout);
                this.timeout = setTimeout(() => {
                    cb(results);
                }, 1000 * Math.random());
            },
            handleSelect(item) {//选中下拉提示的数据时触发
                if (this.All != "") {
                    this.con = true;//显示结果
                }
            },
            onSubmit() {
                if (this.All != "") {//点击查询图标时，显示结果
                    this.con = true;
                }
            }
        },
    };
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .el-autocomplete {
    width: 400px;
  }

  .p2 {
    margin-left: 160px;
    text-align: left;
    font-size: 20px;
  }

  a {
    color: #4f5a75;
  }

  .div2 {
    /* background: blue; */
    margin-top: 25px;
    padding-top: 25px;
    margin-left: 270px;
    width: 750px;
    height: 600px;
    border: 1px solid #b0c4de;
  }
</style>
