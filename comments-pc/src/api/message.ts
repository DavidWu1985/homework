import request from "@/utils/axios";
export class messageApi {
  static async getMessageList() {
    return request({
      url: "/api/comments/list",
      method: "get",
    });
  }
  static async comments(data:any) {
    return request({
      url: "/api/comments",
      method: "POST",
      data
    });
  }
  
 
}
export default messageApi
 