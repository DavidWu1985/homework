<template>
  <div class="list-box">
    <div class="form-box">
      <div class="user-info">
        <div v-if="userInfo.id">
          <span>姓名：{{ userInfo.username }}</span>
          <span>email：{{ userInfo.email }}</span>
        </div>
        <div v-else>用户未登录</div>
      </div>
      <a-form-item>
        <a-textarea
          v-model:value="inputVal"
          :rows="4"
          allow-clear
          show-count
          :maxlength="200"
        />
      </a-form-item>
      <a-form-item>
        <a-button
          html-type="submit"
          :loading="submitting"
          type="primary"
          @click="addMessage(false)"
        >
          Add Comment
        </a-button>
      </a-form-item>
    </div>
    <div>
      <div v-for="(item, index) in listData" :key="index">
        <messageList status="parent" :item="item" @replyshow="replyShowFn" />
      </div>
    </div>
    <Reply
      :visibleData="visible"
      @replyMessage="addMessage"
      @hide="replyShowFn"
    />
  </div>
</template>
<script lang="ts">
import {
  defineComponent,
  reactive,
  toRefs,
  ref,
  getCurrentInstance,
} from "vue";
import messageList from "@/components/messageList.vue";
import Reply from "@/components/reply.vue";
import { messageApi } from "@/api/message";
import { loginApi } from "@/api/login";
import { getCookie } from "@/utils/cookie";
import { useRouter } from "vue-router";
import { message } from "ant-design-vue";

interface DataItem {
  id: number;
  pid: number;
  username: string;
  createDate: string;
  msg: string;
  childs: Array<any>;
}

export default defineComponent({
  components: { messageList, Reply },
  setup() {
    const data = {
      loading: false,
      listData: [],
      visible: false,
      replyPid: "",
      userInfo: {},
    };
    const router = useRouter();

    // 发布评论输入值
    const inputVal = ref("");
    const {
      proxy: { $nextTick, $forceUpdate },
    }: any = getCurrentInstance();

    // 获取评论列表
    const ConutSort = () => {
      messageApi.getMessageList().then((result: any) => {
        if (result.data.code === 100) {
          data.listData = result.data.data;
          $forceUpdate();
        }
      });
    };

    // 获取用户信息
    const getUserInfo = () => {
      const token = getCookie("token") || sessionStorage.getItem("token");
      if (token) {
        loginApi
          .getUserInfo()
          .then((res) => {
            if (res.data.code === 100) {
              data.userInfo = res.data.data;
              $forceUpdate();
            }
          })
          .catch((error: any) => {
            console.log(error);
          });
      }
    };
    getUserInfo();
    // 调用获取方法
    ConutSort();

    // 新增评论
    const addMessage = (val: any) => {
      const token = getCookie("token") || sessionStorage.getItem("token");
      if (token) {
        let params = {};
        if (val) {
          params = val;
        } else {
          if (inputVal.value.length < 3) {
            message.warn("长度应在3～200字之间");
            return;
          }
          params = {
            msg: inputVal.value,
            pid: "",
          };
        }
        messageApi
          .comments(params)
          .then((res: any) => {
            if (res.data.code === 100) {
              // 刷新列表
              data.visible = false;

              ConutSort();
            }
            inputVal.value = "";
          })
          .catch((error: any) => {
            console.log(error);
          });
      } else {
        router.push({ path: "/login" });
      }
    };
    // 显示评论弹窗
    const replyShowFn = (status: boolean) => {
      data.visible = status;
      $forceUpdate();
    };

    const refData = toRefs(data);
    return {
      data,
      inputVal,
      ...refData,
      addMessage,
      replyShowFn,
    };
  },
});
</script>
<style lang="scss">
.list-box {
  width: 800px;
  margin: 20px auto;
  padding: 10px;
  border: 1px solid #ccc;
  text-align: left;
  .user-info {
    padding: 20px 0;
    span {
      padding-right: 20px;
    }
  }
}
</style>
