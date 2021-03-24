package com.miq71.starworks.activity.main.fragment.profile.engineer

import com.miq71.starworks.model.account.AccountResponse
import com.miq71.starworks.model.engineer.EngineerResponse
import com.miq71.starworks.model.skill.SkillModel

interface ProfileEngineerContract {
    interface View {
        fun onResultSuccessAccount(data: AccountResponse.AccountItem)
        fun onResultSuccessEngineer(data: EngineerResponse.EngineerItem)
        fun onResultSuccessSkill(list: List<SkillModel>)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callServiceAccount(acId: Int?)
        fun callServiceEngineer(acId: Int?)
        fun callServiceSkill(enId: Int?)
    }
}