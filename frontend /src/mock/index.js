import Mock from 'mockjs'
import './user'

//使用Mock下面提供的mock方法进行需要模拟数据的封装
//参数1是需要拦截的完整请求地址，参数2是请求方式，参数3是请求的模拟数据
const testData = Mock.mock('http://localhost:8080/test', 'get', {
	status: 200, //请求成功状态码
	dataList: [1, 2, 3, 4, 5] //模拟的请求数据
})

// 入馆与研习室预约系统主页所需数据
// 
// number 当前在馆人数
// notetable 通知公告栏数据
// --- name 公告名
// --- data 发布时间
// --- url 跳转地址
//
//
const CurrentData = Mock.mock('http://localhost:8080/Num', 'get', {
	status: 200, //请求成功状态码
	number: 2524, //模拟的请求数据
	notetable: [{
		id: '0001',
		name: '图书馆关于2022年暑假开馆时间的通知',
		date: '2022-07-06'
	}, {
		id: '0002',
		name: '图书馆限流使用工作提示',
		date: '2022-06-09'
	}, {
		id: '0003',
		name: '图书馆关于读者所借图书延期归还的通知',
		date: '2022-05-17',
	}, {
		id: '0004',
		name: '关于近期毕业离校读者还书的指南',
		date: '2022-05-02',
	}, {
		id: '0005',
		name: '“您眼中的最美图书馆”图片作品征集通知',
		date: '2022-04-25',
	}, {
		id: '0006',
		name: '@所有人，一起为图书馆建言献策吧！',
		date: '2022-02-05',
	}]
})

const { newList } = Mock.mock({
	'newList|20': [
		{
			id: "@increment(1)",
			title: '@ctitle',
			updat: '@date(yyyy-MM-dd hh:mm:ss)',
			info: '@cparagraph(5,10)',
			small: "@dataImage(300x200,电影图片)"
		}
	]

})

Mock.mock('http://localhost:8080/1', 'post', (options) => {
	console.log(options);
	return {
		status: 200,
		message: '请求列表成功！',
		total: 20,
		list: newList

	}
})

Mock.mock('http://localhost:8080/GetMyReservation', 'post', (options) => {
	console.log(options);
	return {
		MyReserveList:[
			{
				type: '个人研习室',
				tag: '101',
				date: '08-15',
				time: '13:00-15:00'
			},
			{
				type: '个人研习室',
				tag: '102',
				date: '08-16',
				time: '13:00-15:00'
			},
			{
				type: '双人研习室',
				tag: '102',
				date: '08-16',
				time: '13:00-15:00'
			}
		]

	}
})

Mock.mock('http://localhost:8080/RoomReserve', 'post', (options) => {
	console.log(options);
	return {

	}
})

Mock.mock('http://localhost:8080/getNotice', 'post', function (options) {
	console.log(options)
	if (options.body === '0001') {
		return {
			title: '图书馆关于2022年暑假开馆时间的通知',
			time: '发表时间：2022-07-06',
			text: '	根据上级部门和学校疫情防控最新文件要求，图书馆优先保障网络资源24小时服务；图书馆阅览室实施有序适度开放，全面实施封闭严格管理。\n\n 一、开闭时间：2022年8月27日起8:00—22:00。\n\n 二、入馆管控：自感有发烧、咳嗽等症状，请勿入馆。所有进出图书馆人员必须携带校园卡，戴口罩，体温检测正常，当日绿码。\n\n 三、预约座位：读者每天预约座位，注意保持社交距离。\n\n 四、环境保障：每日7:00-8:0022:00-22:30为消毒时间，消毒范围包括各阅览室桌椅、地面等以及自助查询机、自助借还机、自助打印机等设备。每日7:00-8:0011:30-13:30 17:00-18:30为通风时间。欢迎读者协助并监督。\n\n 五、个人防护：所有人员在馆全程佩戴口罩，图书馆阅览室服务台均配备免洗净手凝胶，各洗手间放置洗手液，方便读者洗手消毒。\n\n 六、异常处置：如发现异常情况请及时上报。\n\n 以上安排根据学校疫情防控工作相关规定实时调整。\n'
		}
	} else if (options.body === '0002') {
		return {
			title: '图书馆限流使用工作提示',
			time: '发表时间：2022-06-09',
			text: '一、使用对象：符合学校疫情防控要求的本校（片）区在校师生\n\n 二、使用安排 \n 1.使用时间：每日8:00—21:00 \n 2.涉及区域：\n（1）四平路校区：一楼大厅、一楼新书与多媒体阅览室、二楼北期刊阅览室；\n  （2）嘉定校区：一楼大厅、二至三楼阅览区；\n （3）沪西校区：一楼南阅览室；\n （4）沪北校区：一楼阅览室。\n 其它阅览室暂停使用，后续根据各校区疫情防控形势、读者流量、电梯保障情况，适时调整使用时间和区域。\n\n 三、防疫要求 \n 1.入馆前读者请预约入馆名额，每次预约成功可进馆一次；\n 2.各图书馆入口处有专人提醒进入图书馆的读者，遵守防疫要求，入馆读者须满足隔日核酸检测等疫情防控要求；\n 3.读者须扫描入口处的“场所码”，显示为绿码且72小时内核酸检测结果阴性，体温检测正常后可刷校园一卡通入馆。进入各阅览区前，须使用座位管理系统选座或扫描“同学码”签到后方可进入相应的阅览区域；\n 4.在图书馆期间需全程佩戴口罩，严禁餐食、避免聚集。阅览时自觉保持防疫安全距离，按规定就坐。离馆时须刷校园一卡通，并请带走个人物品。\n'
		}
	} else if (options.body === '0003') {
		return {
			title: '图书馆关于读者所借图书延期归还的通知',
			time: '发表时间：2022-05-17',
			text: '各位读者：\n\n	为方便广大师生的工作和学习，大家在各校区图书馆所借图书均可延期至9月30日前归还。在此期间，请大家妥善保管好所借图书。\n	特此通知 \n'
		}
	} else if (options.body === '0004') {
		return {
			title: '关于近期毕业离校读者还书的指南',
			time: '发表时间：2022-05-02',
			text: '	为方便近期毕业离校同学归还图书，图书馆通过咨询电话和邮箱受理还书申请，并结合读者所在校区和楼宇的疫情防控情况开展个性化还书服务。\n\n	图书馆各校区咨询电话：\n		65983994（四平路校区）\n		69589455（嘉定校区）\n		51030060（沪西沪北校区）\n	服务时间：8:00-20:00\n	电子邮箱：shuhai@tongji.edu.cn\n\n	考虑疫情防控因素，目前只接受一周内毕业离校同学的还书申请，敬请谅解。'
		}
	}
})




// 入馆与研习室预约系统单人研习室预约所需数据（双人多人同理）
// 
// singletable1 明天的预约信息
// singletable2 后天的预约信息
// singletable3 大后天的预约信息
// --- type 研习室类型
// --- mode 预约状态
// --- time 预约时段
// --- tag 研习室编号
// 
const SingleData = Mock.mock('http://localhost:8080/Singlenum', 'get', {
	status: 200,
	singletable1: [{
		type: '单人研习室',
		mode: '已预约',
		time: '8:00-10:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '10:00-12:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '13:00-15:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '15:00-17:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '18:00-20:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '8:00-10:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '10:00-12:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '13:00-15:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '15:00-17:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '18:00-20:00',
		tag: '102'
	}],
	singletable2: [{
		type: '单人研习室',
		mode: '未预约',
		time: '8:00-10:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '10:00-12:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '13:00-15:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '15:00-17:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '18:00-20:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '8:00-10:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '10:00-12:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '13:00-15:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '15:00-17:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '18:00-20:00',
		tag: '102'
	}],
	singletable3: [{
		type: '单人研习室',
		mode: '已预约',
		time: '8:00-10:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '10:00-12:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '13:00-15:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '15:00-17:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '18:00-20:00',
		tag: '101'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '8:00-10:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '10:00-12:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '未预约',
		time: '13:00-15:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '15:00-17:00',
		tag: '102'
	}, {
		type: '单人研习室',
		mode: '已预约',
		time: '18:00-20:00',
		tag: '102'
	}],
})

//评分数据
//参数1是需要拦截的完整请求地址，参数2是请求方式，参数3是请求的模拟数据
const scoreData = Mock.mock('http://localhost:8080/score', 'get', {
	status: 200, //请求成功状态码
	num: 5, //评分人数
	currentScore: 4.6, //当前评分 

})


//个人主页的我的借阅数据表
//
//
//
// eslint-disable-next-line no-unused-vars
const borrowData = Mock.mock('http://localhost:8080/borrow', 'get', {
	status: 200, //请求成功状态码
	borrowtableData: [
		{
			isbn: 'F1221C21',
			bookname: '《一间自己的房间》',
			publisher: '北京文艺出版社',
			locate: '嘉定校区',
			starttime: '2022/6/23',
			endtime: '2022/8/22',
			mode: '已归还',
			modeId: 1
		}, {
			isbn: 'F1221C21',
			bookname: '《绿毛水怪》',
			publisher: '重庆文艺出版社',
			locate: '嘉定校区',
			starttime: '2022/6/23',
			endtime: '2022/8/22',
			mode: '未归还',
			modeId: 0
		}, {
			isbn: 'F1221C21',
			bookname: '《刀锋》',
			publisher:'北京文艺出版社',
			locate: '嘉定校区',
			starttime: '2022/6/23',
			endtime: '2022/8/22',
			mode: '超期未还',
			modeId: 2
		}
	],
})



//   个人主页的事务管理页面
//
//   affairtable 事务信息表
//
// eslint-disable-next-line no-unused-vars
const affairData = Mock.mock('http://localhost:8080/affairOrg', 'get', {
	status: 200,
	affairtable: [{
		affairtitle: '订购图书',
		state: 'OK',
		pay: 'card',
		telephone: '1245326472',
		email: 'dawd@163.com'
	}, {
		affairtitle: '退还图书',
		state: 'OK',
		pay: '-',
		telephone: '165477672',
		email: 'jttd@163.com'
	}, {
		affairtitle: '场地预约取消',
		state: 'OK',
		pay: '-',
		telephone: '1324242342',
		email: '6gggg5y@126.com'
	}, {
		affairtitle: '场地出租',
		state: 'OK',
		pay: 'card',
		telephone: '1245326472',
		email: '002cdscs@163.com'
	}, {
		affairtitle: '订购图书',
		state: 'OK',
		pay: 'card',
		telephone: '1245326472',
		email: 'dawd@163.com'
	}, {
		affairtitle: '订购图书',
		state: 'OK',
		pay: 'card',
		telephone: '1245326472',
		email: 'dawd@163.com'
	}, {
		affairtitle: '退还图书',
		state: 'OK',
		pay: '-',
		telephone: '165477672',
		email: 'jttd@163.com'
	},{
		affairtitle: '订购图书',
		state: 'OK',
		pay: 'card',
		telephone: '1245326472',
		email: 'dawd@163.com'
	}, {
		affairtitle: '退还图书',
		state: 'OK',
		pay: '-',
		telephone: '165477672',
		email: 'jttd@163.com'
	}, {
		affairtitle: '场地预约取消',
		state: 'OK',
		pay: '-',
		telephone: '1324242342',
		email: '6gggg5y@126.com'
	}, {
		affairtitle: '场地出租',
		state: 'OK',
		pay: 'card',
		telephone: '1245326472',
		email: '002cdscs@163.com'
	}, {
		affairtitle: '订购图书',
		state: 'OK',
		pay: 'card',
		telephone: '1245326472',
		email: 'dawd@163.com'
	}, {
		affairtitle: '订购图书',
		state: 'OK',
		pay: 'card',
		telephone: '1245326472',
		email: 'dawd@163.com'
	}, {
		affairtitle: '退还图书',
		state: 'OK',
		pay: '-',
		telephone: '165477672',
		email: 'jttd@163.com'
	}]
})

//   个人主页的场地预约页面 所需数据，新增会跳转到 “预约界面”
//
//   placetable 预约信息表
//     start与end必须同一天//注意写判断
//
// eslint-disable-next-line no-unused-vars
const placeData = Mock.mock('http://localhost:8080/placeAppointed', 'get', {
	status: 200,
	placetable: [{
		type: '单人研习室',
		mode: 'OK',
		time: '8:00-10:00',
		date: '2022-6-25',
		tag: '101',
		modeId: 1
	}, {
		type: '双人研习室',
		mode: 'OK',
		time: '10:00-12:00',
		date: '2022-6-25',
		tag: '101',
		modeId: 1
	}, {
		type: '单人研习室',
		mode: 'OK',
		time: '13:00-15:00',
		date: '2022-6-25',
		tag: '101',
		modeId: 0
	}, {
		type: '单人研习室',
		mode: 'OK',
		time: '15:00-17:00',
		date: '2022-6-25',
		tag: '101',
		modeId: 0
	},{
		type: '双人研习室',
		mode: 'OK',
		time: '10:00-12:00',
		date: '2022-6-25',
		tag: '102',
		modeId: 1
	}, {
		type: '三人研习室',
		mode: 'OK',
		time: '13:00-15:00',
		date: '2022-6-25',
		tag: '102',
		modeId: 1
	}]
})

//   个人主页的我的赔书页面
//
//   payfortable 我的赔书表
//
// eslint-disable-next-line no-unused-vars
const payforData = Mock.mock('http://localhost:8080/payFor', 'get', {
	status: 200,
	payfortable: [{
		isbn: 'F1221C21',
		bookname: '《一间自己的房间》',
		publisher: '北京文艺出版社',
		bookid: 'fwef2',
		locate: '嘉定校区',
		borrow: '2022/6/23',
		repay: '23元'
	},{
		isbn: 'Gh213H23',
		bookname: '《撒野》',
		publisher: '浙江教育出版社',
		bookid: 'sadsad2',
		locate: '嘉定校区',
		borrow: '2022/6/23',
		repay: '54元'
	},{
		isbn: 'K1231211',
		bookname: '《文城》',
		publisher: '北京教育出版社',
		bookid: 'fsaf2',
		locate: '嘉定校区',
		borrow: '2022/6/23',
		repay: '32元'
	}]
})




//   个人主页的我的信息页面
//
//   myinftable 事务信息表
//
// eslint-disable-next-line no-unused-vars
const myinfData = Mock.mock('http://localhost:8080/myInf', 'get', {
	status: 200, //请求成功状态码
	username: 'Coconut',
	userstate: 3,
	booknum: 5,
	statenum: 1,
	id: 2052330,
	sex: '女',
	discrib: '这个人什么也没留下~',
	locate: '上海市',
	tele: '1382671283',
	myinftable: [{
		id: '0001',
		name: '“学习闯关”等你来——2022年同济大学图书馆新生教育与信息素养水平测试',
		date: '2022-08-08'
	}, {
		id: '0002',
		name: '鉴往知来——中德谚语对照（3）',
		date: '2022-08-03'
	},  {
		id: '0003',
		name: '图书馆关于读者所借图书延期归还的通知​',
		date: '2022-08-07'
	}, {
		id: '0004',
		name: '图书馆停电通知',
		date: '2022-07-27'
	}, {
		id: '0005',
		name: '大暑｜热散由心静,凉生为室空',
		date: '2022-07-23'
	}]
})

Mock.mock('http://localhost:8080/getNotice2', 'post', function (options) {
	console.log(options)
	if (options.body === '0001') {
		return {
			title: '“学习闯关”等你来——2022年同济大学图书馆新生教育与信息素养水平测试',
			time: '2022-08-08',
			text: '        图书馆里有什么？如何借阅纸本图书？如何使用图书馆的空间和设备？图书馆有哪些常用电子资源？“学习闯关”等你来，这里都可以找到答案哦！\n\n         为帮助2022级同济大学新生尽早熟悉图书馆的资源和服务，我们为大家精心准备了新生指南，针对常用服务和电子资源使用制作了新生微课，其中包括“玩转图书馆、常用电子资源、常用学术工具、科研诚信与学术规范、知识产权”等五大专题，并针对新生群体开发了包含图书馆知识、信息素养知识等的学习闯关测试系统，邀请广大新同学提前了解同济大学图书馆，为今后更好地完成学习、科研奠定基础。\n         一、面向对象：2022级本科生新生、研究生新生\n         二、测试时间：8月8号——9月30号\n         三、“学习闯关”任务介绍\n               学习闯关任务分六大模块设计闯关线路，将图书馆资源和服务的重要知识点以图文、视频、微课、小游戏等不同形式展示，新生通过“学校统一身份认证账号及密码”登录测试系统后按照模块顺序进行学习，每一模块学习结束后完成测试题目即闯关成功。\n         四、学习闯关测试说明\n                  1. 测试内容分本科生和研究生两套题库，根据新生身份分类测试。\n                  2. 闯关测试有两种答题途径，即：PC端和移动端，建议使用PC端（360、火狐、谷歌浏览器效果更佳）。\n                  3. 每位新生有三次闯关测试机会，系统按三次中的最高分记录，每测试完一次需要退出系统后重新登陆开始下一次测试。 \n                  4. 学习测试内容在“同济大学图书馆新生指南”网站“学习闯关”栏目有详细说明。\n                  5. 学习闯关测试系统使用问题，可致电咨询，电话：65982745（周一至周五：8:00 – 17:00）'
		}
	} else if (options.body === '0002') {
		return {
			title: '鉴往知来——中德谚语对照（3）',
			time: '发表时间：2022-06-09',
			text: '        德文图书馆现推出“鉴往知来”——中德谚语专栏。我们选取的每条中国谚语皆尽可能与意思相近的德文谚语对照呈现，并配有充满创意巧思的幽默漫画，西方视角、中国元素、图解谚语，生动、直观、趣味无穷。\n\n  【本期谚语】                                                   独木不成林。\n  【对照德语】                     Ein Baum macht allein noch keinen Wald.\n  【近义德文谚语】          Eine Blume macht keinen Kranz.         一朵花做不成花环。\n\n图文来源：马树德编.《中德谚语对照手册(漫画版)》.北京:商务印书馆,2018.07。\n文字整理：郭可静\n'
		}
	} else if (options.body === '0003') {
		return {
			title: '图书馆关于读者所借图书延期归还的通知',
			time: '发表时间：2022-05-17',
			text: '各位读者：\n        为方便广大师生的工作和学习，大家在各校区图书馆所借图书均可延期至9月30日前归还。在此期间，请大家妥善保管好所借图书。\n        特此通知! \n'
		}
	} else if (options.body === '0004') {
		return {
			title: '图书馆停电通知',
			time: '发表时间：2022-05-02',
			text: '        接学校能源管理中心通知，将于7月30日（星期六）下午对四平路校区图书馆等区域进行停电检修。当天，四平路校区图书馆将在12:00-16:00时间段内临时闭馆，并暂停所有服务。请各位读者注意安全，合理安排进出图书馆时间，离馆时带好个人物品。\n        由于网络断电，其它各校区图书馆的门禁、入馆预约、借还书服务等所有涉及信息系统的服务暂停，进出图书馆采用人工登记验证方式，阅览服务照常。\n如有不便，敬请谅解，感谢您的配合！\n'
		}
	} else if (options.body === '0005') {
		return {
			title: '大暑｜热散由心静,凉生为室空',
			time: '发表时间：2022-05-02',
			text: '        为方便近期毕业离校同学归还图书，图书馆通过咨询电话和邮箱受理还书申请，并结合读者所在校区和楼宇的疫情防控情况开展个性化还书服务。\n	图书馆各校区咨询电话：\n		65983994（四平路校区）\n		69589455（嘉定校区）\n		51030060（沪西沪北校区）\n	服务时间：8:00-20:00\n	电子邮箱：shuhai@tongji.edu.cn\n\n	考虑疫情防控因素，目前只接受一周内毕业离校同学的还书申请，敬请谅解。'
		}
	}

})
