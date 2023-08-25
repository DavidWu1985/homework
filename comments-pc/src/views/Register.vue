<template>
  <div class="register-box">
    <a-form
      :model="formState"
      v-bind="layout"
      name="nest-messages"
      :validate-messages="validateMessages"
      @finish="onFinish"
    >
      <a-form-item
        :name="['user', 'username']"
        label="姓名"
        :rules="[{ required: true }]"
      >
        <a-input v-model:value="formState.user.username" />
      </a-form-item>
      <a-form-item
        :name="['user', 'pswd']"
        label="密码"
        :rules="[{ required: true, validator: checkPswd, trigger: 'change' }]"
      >
        <a-input v-model:value="formState.user.pswd" type="password" />
      </a-form-item>
      <a-form-item
        :name="['user', 'repswd']"
        label="确认密码"
        :rules="[{ required: true , validator: checkRepswd, trigger: 'change'}]"
      >
        <a-input v-model:value="formState.user.repswd" type="password" />
      </a-form-item>
      <a-form-item
        :name="['user', 'email']"
        label="Email"
        :rules="[{required: true , type: 'email' }]"
      >
        <a-input v-model:value="formState.user.email" />
      </a-form-item>

      <a-form-item :wrapper-col="{ ...layout.wrapperCol, offset: 8 }">
        <a-button type="primary" html-type="submit" :loading="loading.value"
          >Submit</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import { requestApi } from "@/api/register";
import { message } from "ant-design-vue";
// import type { RuleObject } from "ant-design-vue/es/form";

import { useRouter } from "vue-router";

export default defineComponent({
  setup(props,{emit}) {
    const layout = {
      labelCol: { span: 8 },
      wrapperCol: { span: 16 },
    };
    const router = useRouter();

    const validateMessages = {
      required: "${label} is required!",
      types: {
        email: "${label} is not a valid email!",
        number: "${label} is not a valid number!",
      },
      number: {
        range: "${label} must be between ${min} and ${max}",
      },
    };

    const formState = reactive({
      user: {
        username: "",
        pswd: "",
        repswd: "",
        email: "",
      },
    });
    let loading = ref(false);
    let checkPswd = async (_rule: any, value: string) => {
      if (!value) {
        return Promise.reject("Please input the password");
      }else{
     return checkPasswordFormat(value);

      }
    };
    let checkRepswd= async (_rule: any, value: string) => {
      if (!value) {
        return Promise.reject("请输入确认密码");
      }else if(value!==formState.user.pswd){
        return Promise.reject("两次密码不一致");

      }else{
        return checkPasswordFormat(value);
      }
    };
    const checkPasswordFormat=(value:string)=>{
      let reg =
        /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$).{6,}$/;
      if (value.length < 5 || value.length > 20) {
        return Promise.reject("密码长度在5～20之间");
      } else {
        let ls = 0;
        if (!value.match(/([a-z])+/)) {
          ls++;
        }
        if (!value.match(/([0-9])+/)) {
          ls++;
        }
        if (!value.match(/([A-Z])+/)) {
          ls++;
        }
        if (!value.match(/[^a-zA-Z0-9]+/)) {
          ls++;
        }
        if (ls > 0) {
          return Promise.reject(
            "密码必须包含一个大写、一个小写、一个数字、一个特殊符合"
          );
        }
      }
    }
    const onFinish = (values: any) => {
      loading.value = true;
      requestApi
        .userRequest(formState.user)
        .then((res) => {
          if (res.data.code === 100) {
            message.success("注册成功");
            setTimeout(() => {
              // router.push({ path: "/login" });
              //父组件传参
              emit('loginshow')
            }, 1000);
          } else {
            message.error(res?.data?.msg || "登录失败");
          }
        })
        .catch((err) => {
          message.error(err);
          loading.value = false;
        });
    };
    return {
      formState,
      layout,
      loading,
      validateMessages,
      onFinish,
      checkPswd,
      checkRepswd,
    };
  },
});
</script>
<style lang="scss">
.register-box {
  width: 800px;
  margin: 30px auto;
  text-align: left;
}
</style>
