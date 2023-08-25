<template>
  <div>
    <a-modal
      :visible="visibleData"
      title="评论"
      ok-text="确认"
      cancel-text="取消"
      @ok="addComment"
      @cancel="hideModal"
    >
      <a-textarea
        v-model:value="inputValue"
        placeholder="请输入评论内容"
        :maxlength="200"
        show-count
        allow-clear
      />
    </a-modal>
  </div>
</template>
<script lang="ts">
import { defineComponent, toRefs, ref } from "vue";
import { message } from "ant-design-vue";

export default defineComponent({
  props: {
    visibleData: {
      type: Boolean,
    },
  },
  setup(props, { emit }) {
    const data = {
      loading: false,
    };
    const inputValue = ref<string>("");
    const addComment = () => {
      let pid = sessionStorage.getItem("replyId");
      console.log("inputValue.value",inputValue.value)
      if (inputValue.value.length < 3) {
        message.warn("长度应在3～200字之间");
        return;
      } else {
        emit("replyMessage", { msg: inputValue.value, pid });
        inputValue.value = "";

      }
    };
    const hideModal = () => {
      inputValue.value = "";
      emit("hide", false);
    };
    const refData = toRefs(data);

    return {
      addComment,
      hideModal,
      confirm,
      inputValue,
      ...refData,
    };
  },
});
</script>
