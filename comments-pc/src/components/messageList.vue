<template>
  <a-comment>
    <template #actions>
      <span key="comment-basic-reply-to" @click.stop="replySelect"
        >Reply to</span
      >
    </template>
    <template #author><a>{{ item.userName }}</a></template>
    <template #avatar>
      <a-avatar
        src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
        alt="Han Solo"
      />
    </template>
    <template #content>
      <p>
        {{ item.msg }}
      </p>
    </template>
    <template #datetime>
        <span>{{item.time}}</span>
    </template>
    <div v-for="child in item.childList" :key="child.id">
      <messageList status="child" :item="child" @replyshow="replyshow" />
    </div>
  </a-comment>
</template>
<script lang="ts">

import { defineComponent } from "vue";
import { getCookie } from "@/utils/cookie";
import { useRouter } from "vue-router";


interface DataItem {
  id: number;
  pid: number;
  username: string;
  createDate: string;
  msg: string;
  childs: Array<any>;
}
export default defineComponent({
  name: "messageList",
  components: {
  },
  props: {
    item: {
      type: Object,
      default: () => {
        return {};
      },
    },
    status: {
      type: String,
    },
  },

  setup(props, { emit }) {
    const router = useRouter();
    const data = {
      likes: 0,
      dislikes: 0,
      action: "",
    };
    const replySelect = () => {
      const token = getCookie("token") || sessionStorage.getItem("token");
      if (token) {
        sessionStorage.setItem("replyId", props.item.id);
        replyshow();
      } else {
        router.push({ path: "/login" });
      }
    };
    const replyshow = () => {
      console.log("replyshow");
      //父组件传参
      emit("replyshow", true);
    };

    return {
      replySelect,
      replyshow,
    };
  },
});
</script>
