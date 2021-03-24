package com.miq71.starworks.activity.main.fragment.profile.company

import com.miq71.starworks.model.account.AccountResponse
import com.miq71.starworks.model.company.CompanyResponse

interface ProfileCompanyContract {
    interface View {
        fun onResultSuccessAccount(data: AccountResponse.AccountItem)
        fun onResultSuccessCompany(data: CompanyResponse.CompanyItem)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callServiceAccount(acId: Int?)
        fun callServiceCompany(acId: Int?)
    }
}