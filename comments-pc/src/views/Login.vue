<template>
  <div class="login-box">
    <div v-if="!registershow" class="form-box">
      <a-tabs v-model:activeKey="activeKey">
        <a-tab-pane key="1" tab="用户名登录">
          <UserLogin @submit="userlogin" />
        </a-tab-pane>
        <a-tab-pane key="2" tab="邮箱登录" force-render
          ><EmailLogin @submit="userlogin" /></a-tab-pane
        >
      </a-tabs>
      <a-button v-on:click="registerPage">Register</a-button>
    </div>
    <UserRegister v-if="registershow" @loginshow="loginshow"/>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, reactive } from "vue";
import UserLogin from "@/components/userLogin.vue";
import EmailLogin from "@/components/emailLogin.vue";
import UserRegister from "./Register.vue";
import { useRouter } from "vue-router";
import { loginApi } from "@/api/login";
import { message } from "ant-design-vue";
import {setCookie} from '@/utils/cookie'
export default defineComponent({
  components: { UserLogin,EmailLogin, UserRegister },
  setup() {
    const router = useRouter();
    let registershow = ref(false);
    const userlogin = (data: any) => {
      loginApi
        .login(data)
        .then((res) => {
          if (res.data.code === 100) {
            message.success("登录成功");
          if(data.remember){
            setCookie("token", res.data.data,4320000)
          }else{
            sessionStorage.setItem("token",  res.data.data);
          }
            // window.localStorage.setItem("token", res.data.data);
            // 登录成功返回列表页
            setTimeout(()=>{
              router.push({ path: "/" });
            },1000)
          } else {
            message.error(res?.data?.msg || "登录失败");
          }
        })
        .catch((error: any) => {
          message.error("登录失败");
          console.error(error);
        });
    };
    const registerPage = () => {
      registershow.value=true;
    };
    const loginshow = () => {
      registershow.value=false;
    }
    const onFinishFailed = (errorInfo: any) => {
      console.log("Failed:", errorInfo);
    };
    return {
      userlogin,
      onFinishFailed,
      registershow,
      registerPage,
      loginshow,
    };
  },
});
</script>

<style lang="scss">
.login-box {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: auto;
  background: #f0f2f5;
  background-image: url(../assets/login-bg.svg);
  background-repeat: no-repeat;
  background-position: center 110px;
  background-size: 100%;
  .form-box {
    width: 500px;
    margin: auto;
    position: absolute;
    display: inline-block;
    height: 300px;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    text-align: left;
  }
}
</style>
