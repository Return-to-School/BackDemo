[TOC]



## PC端

pc端的接口面向的群体是宣传组管理员和超级管理员，主要完成下面功能：

- 创建活动
- 上传活动资料
- 批量审核报名申请
- 查询导出数据
- 对反馈评级









### 用户相关

#### 获取所有的用户

这个接口只有超级管理员可以使用，可以用来查看用户信息，或者为删除用户时提供userid

1. 接口地址：http://localhost:8080/user/all?currPage=1&pageSize=7

2. 请求方式：get

3. 请求参数说明：提供currPage，pageSize

4. 返回参数说明

| 名称 | 类型     | 说明                                 |
| ---- | -------- | ------------------------------------ |
| 无   | json数组 | 返回一个包含所有的user对象的json数组 |

5. 返回参数示例：

```javascript
{
    "pageSize": 7,
    "totalCount": 38,
    "pageNo": 1,
    "hasNextPage": true,
    "totalPageCount": 6,
    "hasPreviousPage": false,
    "result": [
        {
            "userId": "6109117199",
            "password": null,
            "role": {
                "code": 2,
                "desc": "normaluser"
            },
            "loc": null,
            "activities": null
        },
        {
            "userId": "admin1",
            "password": null,
            "role": {
                "code": 0,
                "desc": "superManager"
            },
            "loc": null,
   
        },
        {
            "userId": "admin2",
            "password": null,
            "role": {
                "code": 0,
                "desc": "superManager"
            },
            "loc": null,
 
        },
        {
            "userId": "admin3",
            "password": null,
            "role": {
                "code": 0,
                "desc": "superManager"
            },
            "loc": null,
        
        },
        {
            "userId": "anhui",
            "password": null,
            "role": {
                "code": 0,
                "desc": "superManager"
            },
            "loc": "安徽省",
      
        },
        {
            "userId": "aomen",
            "password": null,
            "role": {
                "code": 0,
                "desc": "superManager"
            },
            "loc": "澳门特别行政区",
   
        }
    ]
}
```



6. 返回字段说明

| 名称            | 说明               |
| --------------- | ------------------ |
| pageSize        | 页面的大小         |
| totalCount      | 数据库的总记录数量 |
| totalPageCount  | 总页数             |
| hasNextPage     | 是否还有下一页     |
| hasPreviousPage | 是否还有上一页     |
| result          | 获取到的数据列表   |







#### 删除用户

这个接口只有超级管理员使用，可能目前不会用到。

1. 接口地址：http://localhost:8080/user/{userId}

2. 请求示例：http://localhost:8080/user/1

3. 功能说明：删除一个id为userId的用户

4. 请求方式：delete

5. 请求参数说明：

| 名称   | 类型   | 说明             |
| ------ | ------ | ---------------- |
| userId | String | 将要删除的用户id |

6. 返回参数示例

   ```
   {
       "success": true,
       "msg": "操作成功",
       "code": 0
   }
   ```

   



### 学生相关

#### 获取参与某个活动的所有学生相关的信息

这个接口可以用于管理员勾选批量审核操作（[点击定位](#管理员批量审核报名申请)）和反馈评级（[点击定位](#管理员评级打分)）返回的数据中包含了四个pojo的信息，`活动信息`,`学生信息`，`申请信息`，`反馈信息`（反馈没有提交则为null）。若学生没有申请该活动，则返回数据中只会包含活动的信息（即applyList字段为空）。

1. 接口地址：http://localhost:8080/student/student-in-act/{activityId}

2. 请求方式：get

3. 请求参数说明：将接口地址中的{activityId}换成数字

4. 请求示例：获取参与id为1的活动的所有学生。http://localhost:8080/student/student-in-act/5

5. 返回参数示例：

```
{
    "msg": "操作成功",
    "code": 0,
    "success": true,
    "data": {
        "applyEndTime": "2020-03-29 09:00:00",
        "creator": "李四",
        "feedbackEndTime": "2020-01-09 09:00:00",
        "filePath": "/星期天",
        "applyList": [
            {
                "feedback": null,
                "activityId": 1,
                "applyId": 2,
                "createTime": "2020-03-27 23:30:19",
                "student": {
                    "studentId": "6109117199",
                    "name": "zhangsan",
                    "gender": {
                        "code": 1,
                        "desc": "男"
                    },
                    "college": "信工",
                    "classname": "计算机111",
                    "idCard": "236183618",
                    "qq": "2313133",
                    "bankCard": "36183718231",
                    "phone": "13767418799",
                    "email": "44213123@qq.com",
                    "origin": "江西",
                    "highSchool": "xx中学"
                },
                "description": "",
                "status": "NOTEXAMINE"
            }
        ],
        "content": "选择正在进行的活动，展示学生列表，管理员可以查看所有学生，宣传组管理员只能查看报名自己所在区域学校的学生信息。点击学生姓名查看学生报名信息点击通过或不通过确认学生报名状态。管理员可以通过省、市、姓名、学院、回访中学、状态等信息，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。列表中可以批量选择进行审核操作。",
        "applyStartTime": "2020-02-02 09:00:00",
        "activityId": 1,
        "createTime": "2020-03-27 00:46:04",
        "name": "星期天",
        "location": "江西省-南昌市-红谷滩新区",
        "needExamine": true,
        "feedbackStartTime": "2020-04-02 09:00:00"
    }
}
```







### 活动相关

#### 创建活动

创建活动时需要加载一个下拉列表（[获取地区数据](#地区相关)）选择对应的省份名称提交作为`location`参数提交，用于建立管理员个活动的映射关系。

<font color = "green">接口地址：http://localhost:8080/activity/{userId}</font>

请求方式：POST

请求参数说明：

| 名称              | 类型    | 必填 | 说明                                   |
| ----------------- | ------- | ---- | -------------------------------------- |
| name              | String  | 是   | 活动名称                               |
| applyStartTime    | date    | 是   | 活动申请开始时间                       |
| applyEndTime      | date    | 是   | 活动申请结束时间                       |
| feedbackStartTime | date    | 是   | 反馈开始时间                           |
| feedbackEndTime   | date    | 是   | 提交反馈结束时间                       |
| creator           | String  | 是   | 活动创建者                             |
| content           | text    | 是   | 活动类容                               |
| location          | String  | 是   | 活动的地区，下拉列表从基础数据库中获取 |
| needExamine       | boolean | 是   | 申请是否需要审核                       |

请求参数示例：

```
{
	"name":"XXX活动",
	"applyStartTime":"2020-01-01 09:00:00",
	"applyEndTime":"2020-03-29 09:00:00",
	"feedbackStartTime":"2020-03-29 17:00:00",
	"feedbackEndTime":"2020-05-01 17:00:00",
	"creator":"张三",
	"content":"选择正在进行的活动，展示学生列表，管理员可以查看所有学生，宣传组管理员只能查看报名自己所在区域学校的学生信息。点击学生姓名查看学生报名信息点击通过或不通过确认学生报名状态。管理员可以通过省、市、姓名、学院、回访中学、状态等信息，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。列表中可以批量选择进行审核操",
	"location":"江西省-南昌市-红谷滩新区",
	"needExamine":1
}
```

返回参数示例：

```
{
    "msg": "操作成功",
    "code": 0,
    "success": true,
    "activityId": 2
}
```

ps：请确保时间的正确性，即活动申请和反馈开始时间要早与结束时间，同时反馈开始时间需要晚于申请结束时间，每次创建活动都会在后台创建两个文件夹，活动资料目录，反馈文件目录，如下图所示：



![1585233567910](C:\Users\maoalong\AppData\Roaming\Typora\typora-user-images\1585233567910.png)







#### 活动资料上传

接口地址：http://localhost:8080/activity/file/{activityId}

请求方式：POST

请求参数说明：

| 名称          | 类型 | 必填 | 说明                                                      |
| ------------- | ---- | ---- | --------------------------------------------------------- |
| activityId    | int  | 是   | 使用restful风格传到url中                                  |
| activityFiles | file | 是   | 在表单中使用的文件name必须为activityFiles，可以支持多文件 |

返回参数示例:

```
{
    "msg": "操作成功",
    "code": 0,
    "success": true
}
```





#### 更新活动

接口地址：http://localhost:8080/activity/{activityId}

请求方式：PUT

请求参数说明：其他同[创建活动](#创建活动)，另外需要添加activityId字段在json对象里面，url需要提供一样的activityId，用于定位哪一个活动

请求参数示例：

```
http://localhost:8080/activity/1
```

```
{
	"activityId":1,
	"name":"yyy活动",
	"applyStartTime":"2020-01-01 09:00:00",
	"applyEndTime":"2020-02-22 09:00:00",
	"feedbackStartTime":"2020-01-29 17:00:00",
	"feedbackEndTime":"2020-03-01 17:00:00",
	"creator":"张三",
	"content":"选择正在进行的活动，展示学生列表，管理员可以查看所有学生，宣传组管理员只能查看报名自己所在区域学校的学生信息。点击学生姓名查看学生报名信息点击通过或不通过确认学生报名状态。管理员可以通过省、市、姓名、学院、回访中学、状态等信息，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。列表中可以批量选择进行审核操",
	"location":"江西省-南昌市-红谷滩新区",
	"needExamine":1
}
```



返回参数：

```
{
    "code": 0,
    "msg": "操作成功",
    "success": true
}
```





#### 删除活动

接口地址：http://localhost:8080/activity/{activityId}

请求方式：delete

请求参数：活动id，接口地址中体现

返回参数示例：

```
{
    "code": 0,
    "success": true,
    "msg": "操作成功"
}
```

> 上面的两个接口只能由活动的管理者或者是超级管理员访问







#### 获取活动下通过审核的申请

接口地址:http://localhost:8080/activity/{id}/apply-pass?currPage=PARAM1&pageSize=PARAM2

请求方式：get

请求示例

```
http://localhost:8080/activity/2/apply-pass?currPage=1&pageSize=5//分页获取活动id为1的所有通过审核的学生信息列表
```

请求参数：

| 名称     | 说明              |
| -------- | ----------------- |
| id       | 活动的id          |
| currPage | 当前页号（1开始） |
| pageSize | 每一页大小        |

参数在接口中体现，不用json传。



返回示例

```
{
    "pageSize": 3,
    "totalCount": 1,
    "pageNo": 1,
    "result": [
        {
            "applyId": 0,
            "createTime": "2020-03-28 00:16:43",
            "status": {
                "code": 1,
                "desc": "通过"
            },
            "description": "",
            "studentId": null,
            "activityId": 0,
            "feedback": null,
            "student": {
                "studentId": "6109117199",
                "name": "zhangsan",
                "gender": {
                    "code": 1,
                    "desc": "男"
                },
                "college": "信工",
                "classname": "计算机111",
                "idCard": "236183618",
                "qq": "2313133",
                "bankCard": "36183718231",
                "phone": "13767418799",
                "email": "44213123@qq.com",
                "origin": "江西",
                "highSchool": "xx中学"
            },
            "activity": {
                "activityId": 2,
                "name": "XXX活动",
                "applyStartTime": "2020-01-01 09:00:00",
                "applyEndTime": "2020-03-29 09:00:00",
                "feedbackStartTime": "2020-03-29 17:00:00",
                "feedbackEndTime": "2020-05-01 17:00:00",
                "creator": "张三",
                "createTime": "2020-03-27 23:36:21",
                "content": "选择正在进行的活动，展示学生列表，管理员可以查看所有学生，宣传组管理员只能查看报名自己所在区域学校的学生信息。点击学生姓名查看学生报名信息点击通过或不通过确认学生报名状态。管理员可以通过省、市、姓名、学院、回访中学、状态等信息，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。列表中可以批量选择进行审核操",
                "filePath": "/XXX活动",
                "location": "江西省-南昌市-红谷滩新区",
                "needExamine": true,
                "applyList": null
            }
        }
    ],
    "hasNextPage": false,
    "totalPageCount": 1,
    "hasPreviousPage": false
}
```





#### 宣传组管理员获取历史活动

可能需要用到这个接口，用于管理员在pc端浏览它已创建活动中的历史活动

接口地址：http://localhost:8080/activity/group/{userId}/history?currPage=1&pageSize=10

请求方式：get

请求参数；页号currPage，页大小pageSize，管理员的账号id userId

| 名称     | 类型   | 说民     |
| -------- | ------ | -------- |
| userid   | String | 账号id   |
| currPage | int    | 当前页号 |
| pageSize | int    | 页面大小 |

上述参数用url传，不用json



请求示例

```
http://localhost:8080/activity/group/jiangxi/history?currPage=1&pageSize=10
```

返回参数示例

```
{
    "pageSize": 10,
    "totalCount": 1,
    "pageNo": 1,
    "totalPageCount": 1,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "result": [
        {
            "activityId": 2,
            "name": "XXX活动",
            "applyStartTime": "2020-01-01 09:00:00",
            "applyEndTime": "2020-02-29 09:00:00",
            "feedbackStartTime": "2020-02-29 17:00:00",
            "feedbackEndTime": "2020-03-01 17:00:00",
            "creator": "张三",
            "createTime": "2020-03-27 23:36:21",
            "content": "选择正在进行的活动，展示学生列表，管理员可以查看所有学生，宣传组管理员只能查看报名自己所在区域学校的学生信息。点击学生姓名查看学生报名信息点击通过或不通过确认学生报名状态。管理员可以通过省、市、姓名、学院、回访中学、状态等信息，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。列表中可以批量选择进行审核操",
            "filePath": "/XXX活动",
            "location": "江西省-南昌市-红谷滩新区",
            "needExamine": true,
            "applyList": null
        }
    ]
}
```

说明：

这里的`applyList`是始终为空的，因为后台没有做连接查询（对于渲染页面没有用），需要获取请[点击](#获取参与某个活动的所有学生相关的信息)









### 申请相关

#### 管理员批量审核报名申请(改变申请状态)

1. 接口地址：http://localhost:8080/apply/examination/{status}

2. 请求方式：post

3. 请求参数说明

| 名称   | 类型      | 说明                    |
| ------ | --------- | ----------------------- |
| status | int       | 0未审核，1通过，2不通过 |
| 无     | jsonArray | 提供所有的applyId       |

4. 请求参数示例：

```
http://localhost:8080/apply/examination/1

[
	{
		"applyId":3
	},
	{
     	"applyId":5   
	}
]
```

5. 返回数据示例：

```
{
    "success": true,
    "msg": "操作成功",
    "code": 0
}
```







#### 超级管理员查询报名申请

​	这个接口为管理员搜索报名申请，包含了四个对象的信息，每一个申请的对应的活动信息，申请信息（包含了申请状态、创建时间等），反馈信息（没有提交则为null），学生信息，渲染出列表后，可以将将这个列表以excel的方式导出([导出接口地址](#超级管理员查询导出))



1. 接口地址：http://localhost:8080/apply/search-for-super?currPage=1&pageSize=3

2. 请求方式：POST

3. 请求参数说明：

| 名称        | 类型   | 说明                          |
| ----------- | ------ | ----------------------------- |
| location    | string | 地区使用中文的“-”分开         |
| studentName | string | 学生姓名                      |
| college     | string | 学院                          |
| highSchool  | string | 回访中学                      |
| applyStatus | int    | 申请转态（枚举类装换，0,1,2） |

> 上面参数不填则为null，意为对该字段不做要求，即该条件为真



4. 请求参数示例

```
{
	"localtion":"江西",
	"studentName":null,
	"college":null,
	"highSchool":null,
	"applyStatus":null
}


{
	"localtion":"江西",
	"studentName":"lisi",
	"college":"金融",
	"highSchool":"yy中学",
	"applyStatus":1
}
```

5. 返回参数示例

ps：未提交反馈的申请中feedback为null

```
{
    "pageSize": 3,
    "totalCount": 2,
    "pageNo": 1,
    "result": [
        {
            "applyId": 5,
            "createTime": "2020-03-28 00:55:56",
            "status": {
                "code": 1,
                "desc": "通过"
            },
            "description": "",
            "studentId": null,
            "activityId": 0,
            "feedback": null,
            "student": {
                "studentId": "6109117189",
                "name": "lisi",
                "gender": {
                    "code": 0,
                    "desc": "女"
                },
                "college": "金融",
                "classname": "金融182",
                "idCard": "1313211",
                "qq": "23123321",
                "bankCard": "123121242",
                "phone": "12322224444",
                "email": "33141@qq.com",
                "origin": "湖南",
                "highSchool": "yy中学"
            },
            "activity": {
                "activityId": 2,
                "name": "XXX活动",
                "applyStartTime": "2020-01-01 09:00:00",
                "applyEndTime": "2020-03-29 09:00:00",
                "feedbackStartTime": "2020-02-29 17:00:00",
                "feedbackEndTime": "2020-03-01 17:00:00",
                "creator": "张三",
                "createTime": "2020-03-27 23:36:21",
                "content": "选择正在进行的活动，展示学生列表，管理员可以查看所有学生，宣传组管理员只能查看报名自己所在区域学校的学生信息。点击学生姓名查看学生报名信息点击通过或不通过确认学生报名状态。管理员可以通过省、市、姓名、学院、回访中学、状态等信息，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。列表中可以批量选择进行审核操",
                "filePath": "/XXX活动",
                "location": "江西省-南昌市-红谷滩新区",
                "needExamine": true,
                "applyList": null
            }
        },
        {
            "applyId": 3,
            "createTime": "2020-03-28 00:16:43",
            "status": {
                "code": 1,
                "desc": "通过"
            },
            "description": "",
            "studentId": null,
            "activityId": 0,
            "feedback": null,
            "student": {
                "studentId": "6109117199",
                "name": "zhangsan",
                "gender": {
                    "code": 1,
                    "desc": "男"
                },
                "college": "信工",
                "classname": "计算机111",
                "idCard": "236183618",
                "qq": "2313133",
                "bankCard": "36183718231",
                "phone": "13767418799",
                "email": "44213123@qq.com",
                "origin": "江西",
                "highSchool": "xx中学"
            },
            "activity": {
                "activityId": 2,
                "name": "XXX活动",
                "applyStartTime": "2020-01-01 09:00:00",
                "applyEndTime": "2020-03-29 09:00:00",
                "feedbackStartTime": "2020-02-29 17:00:00",
                "feedbackEndTime": "2020-03-01 17:00:00",
                "creator": "张三",
                "createTime": "2020-03-27 23:36:21",
                "content": "选择正在进行的活动，展示学生列表，管理员可以查看所有学生，宣传组管理员只能查看报名自己所在区域学校的学生信息。点击学生姓名查看学生报名信息点击通过或不通过确认学生报名状态。管理员可以通过省、市、姓名、学院、回访中学、状态等信息，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。列表中可以批量选择进行审核操",
                "filePath": "/XXX活动",
                "location": "江西省-南昌市-红谷滩新区",
                "needExamine": true,
                "applyList": null
            }
        }
    ],
    "hasPreviousPage": false,
    "totalPageCount": 1,
    "hasNextPage": false
}
```







#### 宣传组管理员查询报名申请

这个接口同[超级管理员查询报名申请](#超级管理员查询报名申请),但是这个接口是给宣传组管理员使用的，与超管不同的是不能使用location进行查询。

接口地址：http://localhost:8080/apply/search-for-group/{userId}?currPage=1&pageSize=3

请求方式：POST

请求参数说明：

| 名称        | 类型   | 说明                          |
| ----------- | ------ | ----------------------------- |
| studentName | string | 学生姓名                      |
| college     | string | 学院                          |
| highSchool  | string | 回访中学                      |
| applyStatus | int    | 申请转态（枚举类装换，0,1,2） |

请求参数示例

```
{
	"studentName":"lisi",
	"college":"金融",
	"highSchool":"yy中学",
	"applyStatus":1
}
```



ps:宣传组管理员不能进行区域搜索

>  上面参数不填则为null，意为对该字段不做要求

返回参数示例

```
{
    "pageSize": 3,
    "totalCount": 1,
    "pageNo": 1,
    "result": [
        {
            "applyId": 5,
            "createTime": "2020-03-28 00:55:56",
            "status": {
                "code": 1,
                "desc": "通过"
            },
            "description": "",
            "studentId": null,
            "activityId": 0,
            "feedback": null,
            "student": {
                "studentId": "6109117189",
                "name": "lisi",
                "gender": {
                    "code": 0,
                    "desc": "女"
                },
                "college": "金融",
                "classname": "金融182",
                "idCard": "1313211",
                "qq": "23123321",
                "bankCard": "123121242",
                "phone": "12322224444",
                "email": "33141@qq.com",
                "origin": "湖南",
                "highSchool": "yy中学"
            },
            "activity": {
                "activityId": 2,
                "name": "XXX活动",
                "applyStartTime": "2020-01-01 09:00:00",
                "applyEndTime": "2020-03-29 09:00:00",
                "feedbackStartTime": "2020-02-29 17:00:00",
                "feedbackEndTime": "2020-03-01 17:00:00",
                "creator": "张三",
                "createTime": "2020-03-27 23:36:21",
                "content": "选择正在进行的活动，展示学生列表，管理员可以查看所有学生，宣传组管理员只能查看报名自己所在区域学校的学生信息。点击学生姓名查看学生报名信息点击通过或不通过确认学生报名状态。管理员可以通过省、市、姓名、学院、回访中学、状态等信息，宣传组管理员可以通过姓名、学院、回访中学、状态等信息进行查询及导出。列表中可以批量选择进行审核操",
                "filePath": "/XXX活动",
                "location": "江西省-南昌市-红谷滩新区",
                "needExamine": true,
                "applyList": null
            }
        }
    ],
    "totalPageCount": 1,
    "hasPreviousPage": false,
    "hasNextPage": false
}
```







### 反馈相关

#### 管理员评价打分

管理员评级打分分三级，不合格，合格，优秀，使用枚举类存储了这个等级信息，所有只要提供对应的code即可，0不合格，1合格,2优秀）

1. 接口地址：http://localhost:8080/feedback/1/score?level=3
2. 请求方式：get
3. 请求参数：

| 名称  | 类型 | 说明                                                         |
| ----- | ---- | ------------------------------------------------------------ |
| level | int  | 评价结果枚举类，提供对应的代码即可（0,1,2不合格，合格，优秀递增） |

4. 返回示例

```
	{
    "code": 444,
    "msg": "level out of range",
    "succees": false
}
------------------------------
{
    "success": true,
    "code": 0,
    "msg": "操作成功"
}
------------------------------
{
    "success": false,
    "code": 202,
    "msg": "数据库无记录"
}
```





### excel导出

#### 超级管理员查询导出

这个接口的作用是利用前面查询到的数据然后导出excel文件，请求参数key不变，但是不用提供分页需要的数据

1. 接口地址：http://localhost:8080/apply/export-for-super

2. 请求方式：POST

3. 请求示例：

```
http://localhost:8080/apply/export-for-super
```

4. 请求参数：

| 名称        | 类型   | 说明                          |
| ----------- | ------ | ----------------------------- |
| location    | string | 地区使用中文的“-”分开         |
| studentName | string | 学生姓名                      |
| college     | string | 学院                          |
| highSchool  | string | 回访中学                      |
| applyStatus | int    | 申请转态（枚举类装换，0,1,2） |

5. 请求示例

```
{
	"localtion":null,
	"studentName":null,
	"college":null,
	"highSchool":null,
	"applyStatus":1
}
```



6. 返回数据示例(excel文件)：

| 姓名     | 学号       | 学院 | 专业班级  | 籍贯        | 活动地区                 | 回访中学 | 报名状态 | 评价结果 |
| -------- | ---------- | ---- | --------- | ----------- | ------------------------ | -------- | -------- | -------- |
| lisi     | 6109117189 | 金融 | 金融182   | 江西-南昌市 | 江西省-南昌市-红谷滩新区 | yy中学   | 通过     | 未提交   |
| zhangsan | 6109117199 | 信工 | 计算机111 | 江西-南昌市 | 江西省-南昌市-红谷滩新区 | xx中学   | 通过     | 优秀     |





#### 宣传组管理员查询导出

1. 接口地址：http://localhost:8080/apply/export-for-group/{userId}

2. 请求方式：POST

3. 请求示例：

```
http://localhost:8080/apply/export-for-group/jiangxi
```

4. 请求参数：同宣传组管理员查询报名申请，不需要提供分页需要的参数

5. 返回数据示例：同上

