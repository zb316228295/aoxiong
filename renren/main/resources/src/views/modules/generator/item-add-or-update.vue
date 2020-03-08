<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="creatTime">
      <el-input v-model="dataForm.creatTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="changeTime">
      <el-input v-model="dataForm.changeTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="status">
      <el-input v-model="dataForm.status" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="name">
      <el-input v-model="dataForm.name" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="brief">
      <el-input v-model="dataForm.brief" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="describe">
      <el-input v-model="dataForm.describe" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="pics">
      <el-input v-model="dataForm.pics" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          creatTime: '',
          changeTime: '',
          status: '',
          name: '',
          brief: '',
          describe: '',
          pics: ''
        },
        dataRule: {
          creatTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          changeTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          brief: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          describe: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          pics: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/generator/item/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.creatTime = data.item.creatTime
                this.dataForm.changeTime = data.item.changeTime
                this.dataForm.status = data.item.status
                this.dataForm.name = data.item.name
                this.dataForm.brief = data.item.brief
                this.dataForm.describe = data.item.describe
                this.dataForm.pics = data.item.pics
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/generator/item/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'creatTime': this.dataForm.creatTime,
                'changeTime': this.dataForm.changeTime,
                'status': this.dataForm.status,
                'name': this.dataForm.name,
                'brief': this.dataForm.brief,
                'describe': this.dataForm.describe,
                'pics': this.dataForm.pics
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
