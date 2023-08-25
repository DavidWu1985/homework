import request from "@/utils/axios";
export class requestApi {
  static async userRequest(params: any) {
    return request({
      url: "/api/register",
      method: "post",
      data:params,
    });
  }
}
export default requestApi