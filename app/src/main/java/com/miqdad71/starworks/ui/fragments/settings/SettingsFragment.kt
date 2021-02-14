package com.miqdad71.starworks.ui.fragments.settings

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.account.AccountResponse
import com.miqdad71.starworks.data.model.company.CompanyResponse
import com.miqdad71.starworks.data.model.engineer.EngineerResponse
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.serviceapi.CompanyAPI
import com.miqdad71.starworks.serviceapi.EngineerAPI
import kotlinx.coroutines.*

class SettingsFragment(private val ids: Int, private val acId: Int, private val level: Int) : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var viewModel: SettingsViewModel

    private lateinit var acName: EditTextPreference
    private lateinit var acEmail: EditTextPreference
    private lateinit var acPhone: EditTextPreference

    private lateinit var enJobTitle: ListPreference
    private lateinit var enJobType: ListPreference
    private lateinit var enDomicile: EditTextPreference
    private lateinit var enDescription: EditTextPreference

    private lateinit var cnCompany: EditTextPreference
    private lateinit var cnPosition: EditTextPreference
    private lateinit var cnField: EditTextPreference
    private lateinit var cnCity: EditTextPreference
    private lateinit var cnDescription: EditTextPreference
    private lateinit var cnInstagram: EditTextPreference
    private lateinit var cnLinkedin: EditTextPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        if (level == 0) {
            setPreferencesFromResource(R.xml.settings_engineer, rootKey)
            coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

            initAccount()
            initEngineer()
            prefsAccount()
            prefsEngineer()
        } else {
            setPreferencesFromResource(R.xml.settings_company, rootKey)
            coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

            initAccount()
            initCompany()
            prefsAccount()
            prefsCompany()
        }

        setViewModel()
        subscribeLiveData()
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        when (preference?.key) {
            "ac_name" -> {
                viewModel.serviceUpdateAccount(
                    field = "ac_name",
                    value = "$newValue",
                    acId = acId
                )
            }
            "ac_email" -> {
                viewModel.serviceUpdateAccount(
                    field = "ac_email",
                    value = "$newValue",
                    acId = acId
                )
            }
            "ac_phone" -> {
                viewModel.serviceUpdateAccount(
                    field = "ac_phone",
                    value = "$newValue",
                    acId = acId
                )
            }
        }

        when (preference?.key) {
            "en_job_title" -> {
                viewModel.serviceUpdateEngineer(
                    field = "en_job_title",
                    value = "$newValue",
                    enId = ids
                )
            }
            "en_job_type" -> {
                viewModel.serviceUpdateEngineer(
                    field = "en_job_type",
                    value = "$newValue",
                    enId = ids
                )
            }
            "en_domicile" -> {
                viewModel.serviceUpdateEngineer(
                    field = "en_domicile",
                    value = "$newValue",
                    enId = ids
                )
            }
            "en_description" -> {
                viewModel.serviceUpdateEngineer(
                    field = "en_description",
                    value = "$newValue",
                    enId = ids
                )
            }
        }

        when (preference?.key) {
            "cn_company" -> {
                viewModel.serviceUpdateCompany(
                    field = "cn_company",
                    value = "$newValue",
                    cnId = ids
                )
            }
            "cn_position" -> {
                viewModel.serviceUpdateCompany(
                    field = "cn_position",
                    value = "$newValue",
                    cnId = ids
                )
            }
            "cn_field" -> {
                viewModel.serviceUpdateCompany(
                    field = "cn_field",
                    value = "$newValue",
                    cnId = ids
                )
            }
            "cn_city" -> {
                viewModel.serviceUpdateCompany(
                    field = "cn_city",
                    value = "$newValue",
                    cnId = ids
                )
            }
            "cn_description" -> {
                viewModel.serviceUpdateCompany(
                    field = "cn_description",
                    value = "$newValue",
                    cnId = ids
                )
            }
            "cn_instagram" -> {
                viewModel.serviceUpdateCompany(
                    field = "cn_instagram",
                    value = "$newValue",
                    cnId = ids
                )
            }
            "cn_linkedin" -> {
                viewModel.serviceUpdateCompany(
                    field = "cn_linkedin",
                    value = "$newValue",
                    cnId = ids
                )
            }
        }

        return true
    }

    private fun initAccount() {
        acName = findPreference("ac_name")!!
        acEmail = findPreference("ac_email")!!
        acPhone = findPreference("ac_phone")!!
    }

    private fun initEngineer() {
        enJobTitle = findPreference("en_job_title")!!
        enJobType = findPreference("en_job_type")!!
        enDomicile = findPreference("en_domicile")!!
        enDescription = findPreference("en_description")!!
    }

    private fun initCompany() {
        cnCompany = findPreference("cn_company")!!
        cnPosition = findPreference("cn_position")!!
        cnField = findPreference("cn_field")!!
        cnCity = findPreference("cn_city")!!
        cnDescription = findPreference("cn_description")!!
        cnInstagram = findPreference("cn_instagram")!!
        cnLinkedin = findPreference("cn_linkedin")!!
    }

    private fun prefsAccount() {
        acName.onPreferenceChangeListener = this@SettingsFragment
        acEmail.onPreferenceChangeListener = this@SettingsFragment
        acPhone.onPreferenceChangeListener = this@SettingsFragment

        setAccount()
    }

    private fun prefsEngineer() {
        enJobTitle.onPreferenceChangeListener = this@SettingsFragment
        enJobType.onPreferenceChangeListener = this@SettingsFragment
        enDomicile.onPreferenceChangeListener = this@SettingsFragment
        enDescription.onPreferenceChangeListener = this@SettingsFragment

        setEngineer()
    }

    private fun prefsCompany() {
        cnCompany.onPreferenceChangeListener = this@SettingsFragment
        cnPosition.onPreferenceChangeListener = this@SettingsFragment
        cnField.onPreferenceChangeListener = this@SettingsFragment
        cnCity.onPreferenceChangeListener = this@SettingsFragment
        cnDescription.onPreferenceChangeListener = this@SettingsFragment
        cnInstagram.onPreferenceChangeListener = this@SettingsFragment
        cnLinkedin.onPreferenceChangeListener = this@SettingsFragment

        setCompany()
    }

    private fun setAccount() {
        coroutineScope.launch {
            val service = activity?.let { ApiClient.getApiClient(it).create(AccountAPI::class.java) }

            val response = withContext(Dispatchers.IO) {
                try {
                    service?.detailAccount(acId)
                } catch (t: Throwable) {
                    Log.d("msg", "${t.message}")
                }
            }

            if (response is AccountResponse) {
                val data = response.data[0]

                acName.text = data.acName
                acEmail.text = data.acEmail
                acPhone.text = data.acPhone
            }
        }
    }

    private fun setEngineer() {
        coroutineScope.launch {
            val service = activity?.let { ApiClient.getApiClient(it).create(EngineerAPI::class.java) }

            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getDetailEngineer(acId)
                } catch (t: Throwable) {
                    Log.d("msg", "${t.message}")
                }
            }

            if (response is EngineerResponse) {
                val data = response.data[0]

                enJobTitle.value = data.enJobTitle
                enJobType.value = data.enJobType
                enDomicile.text = data.enDomicile
                enDescription.text = data.enDescription
            }
        }
    }

    private fun setCompany() {
        coroutineScope.launch {
            val service = activity?.let { ApiClient.getApiClient(it).create(CompanyAPI::class.java) }

            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getDetailCompany(acId)
                } catch (t: Throwable) {
                    Log.d("msg", "${t.message}")
                }
            }

            if (response is CompanyResponse) {
                val data = response.data[0]

                cnCompany.text = data.cnCompany
                cnPosition.text = data.cnPosition
                cnField.text = data.cnField
                cnCity.text = data.cnCity
                cnDescription.text = data.cnDescription
                cnInstagram.text = data.cnInstagram
                cnLinkedin.text = data.cnLinkedin
            }
        }
    }

    private fun setViewModel() {
        val serviceAccount = activity?.let { ApiClient.getApiClient(it).create(AccountAPI::class.java) }
        val serviceEngineer = activity?.let { ApiClient.getApiClient(it).create(EngineerAPI::class.java) }
        val serviceCompany = activity?.let { ApiClient.getApiClient(it).create(CompanyAPI::class.java) }

        viewModel = ViewModelProvider(this@SettingsFragment).get(SettingsViewModel::class.java)
        viewModel.setService(
            serviceAccount = serviceAccount!!,
            serviceEngineer = serviceEngineer!!,
            serviceCompany = serviceCompany!!
        )
    }

    private fun subscribeLiveData() {
        viewModel.onSuccessLiveData.observe(this@SettingsFragment) {
            if (it.isNotEmpty()) {
                Log.d("msg", "Success")
            } else {
                Log.d("msg", "Error!")
            }
        }

        viewModel.onFailLiveData.observe(this@SettingsFragment) {
            Log.d("msg", it)
        }
    }
}