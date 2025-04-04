    package com.example.edusfe.adapter

    import androidx.fragment.app.Fragment
    import androidx.fragment.app.FragmentManager
    import androidx.fragment.app.FragmentPagerAdapter
    import com.example.edusfe.ui.fragment.tabLayout.MyCommentFragment
    import com.example.edusfe.ui.fragment.tabLayout.MyThreadFragment

    class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            var fragment = Fragment()
            when (position) {
                0 -> fragment = MyThreadFragment()
                1 -> fragment = MyCommentFragment()
            }

            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Thread"
                1 -> "Comment"
                else -> null
            }
        }
    }