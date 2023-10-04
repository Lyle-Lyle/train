import { createStore } from 'vuex'

const MEMBER = "MEMBER";

export default createStore({
  state: {
    // 缓存中如果有值就取出来，不然就是空，并且避免了空指针异常
    member: window.SessionStorage.get(MEMBER) || {}
  },
  getters: {
  },
  mutations: {
    setMember (state, _member) {
      state.member = _member;
      window.SessionStorage.set(MEMBER, _member);
    }
  },

  // 异步任务
  actions: {
  },
  // 如果项目比较大，还可以分模块，就像namespace一样
  modules: {
  }
})
