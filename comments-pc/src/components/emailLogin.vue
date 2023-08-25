<template>
  <div>
    <a-form
      :model="formState"
      name="basic"
      :label-col="{ span: 8 }"
      :wrapper-col="{ span: 16 }"
      autocomplete="off"
      @finish="onFinish"
      @finishFailed="onFinishFailed"
    >
      <a-form-item
        label="E-mail"
        name="name"
        :rules="[
          {
            required: true,
            type: 'email',
            message: 'Please input your email!',
          },
        ]"
      >
        <a-input v-model:value="formState.name" />
      </a-form-item>
      <a-form-item
        label="Password"
        name="pswd"
        :rules="[{ required: true, message: 'Please input your password!' }]"
      >
        <a-input-password v-model:value="formState.pswd" />
      </a-form-item>

      <a-form-item name="remember" :wrapper-col="{ offset: 8, span: 16 }">
        <a-checkbox v-model:checked="formState.remember"
          >Remember me</a-checkbox
        >
      </a-form-item>

      <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
        <a-button type="primary" html-type="submit">Submit</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue";
interface FormState {
  name: string;
  pswd: string;
  remember: boolean;
}
export default defineComponent({
  setup(props, { emit }) {
    const formState = reactive<FormState>({
      name: "admin@163.com",
      pswd: "admin",
      remember: true,
    });
    const onFinish = (values: any) => {
      // console.log("Success:", values);
      emit("submit", values);
    };

    const onFinishFailed = (errorInfo: any) => {
      // console.log("Failed:", errorInfo);
    };
    return {
      formState,
      onFinish,
      onFinishFailed,
    };
  },
});
</script>

<style></style>
