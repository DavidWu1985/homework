//获取cookie、
export function getCookie(name:string) {
  let arr;
  const reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
  // eslint-disable-next-line no-cond-assign
  if (arr = document.cookie.match(reg))
   return (arr[2]);
  else
   return null;
 }
 
 //设置cookie,增加到vue实例方便全局调用
 export function setCookie (c_name:string, value:string, expiredays:number) {
  const exdate = new Date();
  exdate.setDate(exdate.getDate() + expiredays);
  document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toUTCString());
 }
 
 //删除cookie
 export function delCookie (name:string) {
  const exp = new Date();
  exp.setTime(exp.getTime() - 1);
  const cval = getCookie(name);
  if (cval != null)
   document.cookie = name + "=" + cval + ";expires=" + exp.toUTCString();
 }
 
 