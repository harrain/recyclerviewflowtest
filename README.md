- RecyclerView瀑布流出现跳动空白

> 根源是StaggeredGridAdapter在滑动时无法使ImageView确定宽高

> 解决办法： 使用ScaleImageView替换原生ImageView

- screenshot

<img src="/screenshot/recyclerviewflow.gif" width="540px" height="960px" />