package shirin.tahmasebi.mscfinalproject.inlineBrowser;

public class InlineBrowserPresenter implements InlineBrowserInteractor.InlineBrowserListener {
    InlineBrowserView mView;
    InlineBrowserInteractor mInteractor;

    public InlineBrowserPresenter(InlineBrowserView view) {
        mView = view;
        mInteractor = new InlineBrowserInteractor(this);
    }

    public void loadUrl(android.content.Context context) {
        final String EXTRA_URL = "customurl";
        String url = ((InlineBrowserActivity) context).getIntent().getExtras().getString(EXTRA_URL);
        mView.openUrl(url);
    }

    public interface InlineBrowserView {
        void openUrl(String url);
    }
}
