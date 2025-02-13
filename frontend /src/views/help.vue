<template>
    <h1 style="font-size: 20px; padding:1%">网上入馆与研习室预约系统使用说明</h1>
    <div class="tabs">
        <span style="line-height:35px; white-space: pre-wrap;font-size: 15px;">
            {{ helptext}}
            </span>
    </div>
</template>

<script>

export default {
    name: 'Help',
    data() {
        return {
            helptext: '1.登录成功后在主页可以选择预约入馆，取消预约之前不能重复预约。\n\n2.主页可以查看本账号预约的研习室信息并可确认信息无误后选择取消预约。\n\n3.左侧导航栏中罗列了目前可以预约使用的资源列表，点击选择不同类型的研习室进行预约。\n\n4.场地预约顶部显示当前日期，在下面的标签页可以选择要预约的日期（目前支持未来三天的场地预约），确认预约信息后提交即可完成研习室预约。\n\n5.每条预约限制为两小时，已被预约的条目会显示无法预约。预约成功后，读者可在预约时间内到预约的研习室刷卡开门。\n\n',
        };
    },
    mounted() {
        
    },
    setup() {
        
    },
    methods: {

    }
}
</script>

<style lang="less" scoped>
.tabs {
    height: 87%;
    width: 98%;
    padding: 3%;
    background-color: white;
}
</style>