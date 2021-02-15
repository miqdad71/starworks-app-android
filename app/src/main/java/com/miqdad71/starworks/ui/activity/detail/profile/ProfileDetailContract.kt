package com.miqdad71.starworks.ui.activity.detail.profile

import com.miqdad71.starworks.data.model.account.AccountResponse
import com.miqdad71.starworks.data.model.engineer.EngineerResponse
import com.miqdad71.starworks.data.model.skill.SkillModel

interface ProfileDetailContract {
    interface View {
        fun onResultSuccessAccount(data: AccountResponse.AccountItem)
        fun onResultSuccessEngineer(data: EngineerResponse.EngineerItem)
        fun onResultSuccessSkill(list: List<SkillModel>)
        fun onResultSuccessHire(status: Boolean)
        fun onResultFail(message: String)
        fun onResultFailHire(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callServiceAccount(acId: Int?)
        fun callServiceEngineer(acId: Int?)
        fun callServiceSkill(enId: Int?)
        fun callServiceIsHire(cnId: Int?, enId: Int?)
    }
}