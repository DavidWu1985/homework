import request from "@/utils/axios";
export class loginApi {
  static async login(data: any) {
    return request({
      url: "/api/login",
      method: "POST",
      data,
      params:data,
    });
  }
  static async getUserInfo() {
    return request({
      url: "/api/current_user",
      method: "GET",
    });
  }
}
export default loginApi
