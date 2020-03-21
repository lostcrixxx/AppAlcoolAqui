package br.com.envolvedesenvolve.alcoolaqui.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateMarkViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateMarkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Adicionar local");
    }

    public LiveData<String> getText() {
        return mText;
    }
}