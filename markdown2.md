1. 与前端页面机器人模拟催收  
  * [打开页面](#打开页面)
  * [刷新](#刷新)
  * [开始会话](#开始会话)
  * [结束会话](#结束会话)
  * [获取对话记录](#获取对话记录)
  
2. 与IVR进行文本交互
  * [开启与IVR的文本交互](#开启与IVR的文本交互) 
  * [后续与IVR的文本交互](#后续与IVR的文本交互) 


<h2 id="打开页面">打开页面</h2>  
接口地址 : `https://xiaocui-staging.zichan360.com/Pbt/demo/open`  
提交方式 : GET/POST  
<pre>

</pre>

<h2 id="刷新">刷新</h2>
接口地址 : `https://xiaocui-staging.zichan360.com/Pbt/demo/refresh`  
提交方式 : GET/POST  
<pre>

</pre>

<h2 id="开始会话">开始会话</h2>
接口地址 : `https://xiaocui-staging.zichan360.com/Pbt/demo/start`  
提交方式 : POST  
提交参数 : Json对象

<pre>
  {	
   	  "name":"债务人姓名",		
   	  "overdueDay":"逾期天数",		
   	  "debt":"逾期总额",		
   	  "capital":"逾期本金",		
   	  "sex":"债务人性别  0:男  1:女",			
   	  "date":"借款时间",	
   	  "product":"产品名称",	
   	  "interest":"逾期利息"		
  }
</pre>
example
<pre>
{
  "debtorId":"111",	
  "name":"关羽",		
  "overdueDay":"5",		
  "debt":"10000",		
  "capital":"8000",		
  "sex":"0",			
  "date":"2018-12-01",	
  "product":"呼呼贷",	
  "interest":"2000"		
}
</pre>
返回数据：
<pre>
{
  "data": "任务已启动"
}  
</pre>

<h2 id="结束会话">结束会话</h2>
接口地址 : `https://xiaocui-staging.zichan360.com/Pbt/text/end`  
提交方式 :  GET/POST
提交参数 : 
返回数据 :
<pre>
{
   "data": "任务已经停止"
}
</pre>

<h2 id="获取对话记录">获取对话记录</h2>
接口地址 : `https://xiaocui-staging.zichan360.com/Pbt/text/getChatRecord`  
提交方式 :  GET/POST  
提交参数 :  
返回数据 :
<pre>
{
   "data": "对话记录"
}
</pre>
example:
<pre>
{
    "data": "您好，我是闪银委外机构的催收员小崔, 请问是关羽先生吗？"
}
</pre>


<h2 id="开启与IVR的文本交互">开启与IVR的文本交互</h2>  
接口地址 : `https://xiaocui-staging.zichan360.com/Pbt/text/start `  
提交方式 : POST  
提交参数 : Json对象
<pre>
  {
   	  "debtorId":"债务人Id(债务人唯一标识)",	
   	  "name":"债务人姓名",		
   	  "overdueDay":"逾期天数",		
   	  "debt":"逾期总额",		
   	  "capital":"逾期本金",		
   	  "sex":"债务人性别  0:男  1:女",			
   	  "date":"借款时间",	
   	  "product":"产品名称",	
   	  "interest":"逾期利息"		
  }
</pre>  
example:
<pre>
  {
   	  "debtorId":"111",	
   	  "name":"关羽",		
   	  "overdueDay":"5",		
   	  "debt":"10000",		
   	  "capital":"8000",		
   	  "sex":"0",			
   	  "date":"2018-12-01",	
   	  "product":"呼呼贷",	
   	  "interest":"2000"		
  }

</pre>

返回数据 :  Json对象
<pre>
{  
  "msg": "success或fail",
  "data":"催收话语($$之间表示变量)",      
  "deborId":"债务人Id(债务人唯一标识)"	
}
</pre>
example:

<pre>
{
    "msg": "success",
    "data": "您好，我是闪银委外机构的催收员小崔, 请问是$关羽先生$吗？",
    "debtorId": 111
}
</pre>


<h2 id="后续与IVR的文本交互">后续与IVR的文本交互</h2>  
接口地址 : `https://xiaocui-staging.zichan360.com/Pbt/text/getIvrMessage`  
提交方式 : POST  
提交参数 : Json对象
<pre>
{
    "data":"债务人话语",				
    "debtorId": "债务人Id(债务人唯一标识)"			
}
</pre>  
example:
<pre>
 {"data":"是的" , "debtor": "111"}
</pre>


返回数据 :  Json对象

<pre>
  {
    "msg": "success或fail"	,
    "data": "催收话语($$之间表示变量)",
    "deborId": "债务人Id(债务人唯一标识)"		  		
  }
</pre>

example:
<pre>
{
   "msg": "success",
   "data": "您好，您在$呼呼贷$这笔$10000$元欠款已经逾期$5$天了，您今天几点能处理好这笔款项呢？",
   "deborId": 111
}
</pre>

