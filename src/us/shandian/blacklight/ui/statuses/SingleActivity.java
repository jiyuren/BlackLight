package us.shandian.blacklight.ui.statuses;

import android.app.Fragment;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentStatePagerAdapter;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import java.util.List;

import us.shandian.blacklight.R;
import us.shandian.blacklight.cache.statuses.HomeTimeLineApiCache;
import us.shandian.blacklight.model.MessageModel;
import us.shandian.blacklight.model.MessageListModel;
import us.shandian.blacklight.ui.comments.StatusCommentFragment;

public class SingleActivity extends SwipeBackActivity
{
	private MessageModel mMsg;
	
	private Fragment mMsgFragment;
	private Fragment mCommentFragment;
	private Fragment mRepostFragment;
	
	private ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single);
		
		// Action Bar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		// Arguments
		mMsg = getIntent().getParcelableExtra("msg");
		
		// Init
		mMsgFragment = new HackyFragment();
		mCommentFragment = new StatusCommentFragment(mMsg.id);
		mRepostFragment = new RepostTimeLineFragment(mMsg.id);
		getFragmentManager().beginTransaction().add(R.id.single_content, mMsgFragment).commit();
		
		mPager = (ViewPager) findViewById(R.id.single_pager);
		mPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
			@Override
			public int getCount() {
				return 2;
			}

			@Override
			public Fragment getItem(int position) {
				switch (position) {
					case 0:
						return mCommentFragment;
					case 1:
						return mRepostFragment;
					default:
						return null;
				}
			}
		});
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class HackyApiCache extends HomeTimeLineApiCache {
		public HackyApiCache(Context context) {
			super(context);
			mMessages = new MessageListModel();
			((List<MessageModel>) mMessages.getList()).add(mMsg);
		}

		@Override
		public void loadFromCache() {
			
		}

		@Override
		public void load(boolean newWeibo) {
			
		}
		
		@Override
		public void cache() {
			
		}
	}
	
	private class HackyFragment extends HomeTimeLineFragment {
		
		@Override
		protected void bindFooterView(LayoutInflater inflater) {
			
		}

		@Override
		protected void bindSwipeToRefresh(ViewGroup v) {
			
		}

		@Override
		protected HomeTimeLineApiCache bindApiCache() {
			return new HackyApiCache(getActivity());
		}
		
		@Override
		protected void initTitle() {
			
		}
	}
}
