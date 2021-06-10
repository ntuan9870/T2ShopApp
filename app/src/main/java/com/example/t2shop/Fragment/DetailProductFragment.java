package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.t2shop.Activity.MainActivity;
import com.example.t2shop.Adapter.CommentAdapter;
import com.example.t2shop.Adapter.RatingAdapter;
import com.example.t2shop.Common.Common;
import com.example.t2shop.Common.Common2;
import com.example.t2shop.Database.T2ShopDatabase;
import com.example.t2shop.Model.Category;
import com.example.t2shop.Model.ItemCart;
import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.Promotion;
import com.example.t2shop.Model.User;
import com.example.t2shop.R;
import com.example.t2shop.Response.ResponseComment;
import com.example.t2shop.Response.ResponseFavorite;
import com.example.t2shop.Response.ResponseMessage;
import com.example.t2shop.Response.ResponseOrder;
import com.example.t2shop.Response.ResponseRatingAll;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DetailProductFragment extends Fragment {
    private AppBarLayout appBarLayoutDetailProduct;
    private ImageView img_menu_detail_product, img_shopping_cart_detail_product, img_home_detail_product, img_search_detail_product,
            img_back_detail_product, img_detail_product, detail_favorite_product;
    private TextView txt_name_detail_product, txt_price_detail_product, txt_price_original_detail_product, txt_promotion_detail_product,
            txt_number_rating_detail_product, txt_category_detail_product, txt_warranty_detail_product, txt_accessories_detail_product;
    private TextView txt_conditional_detail_product, txt_description_detail_product, txt_rating_detail_product, txt_number_rating_detail_product2, txt_rating_user,
            txt_login;
    private RatingBar rating_detail_product, rating_detail_product2;
    private ProgressBar progress_5_star, progress_4_star, progress_3_star, progress_2_star, progress_1_star;
    private Button btn_add_to_cart, btn_send_comment, btn_send_star;
    public static String TAG = DetailProductFragment.class.getName();
    private TextInputLayout edt_comment;
    private LinearLayout ln_comment,ln_rating_user, ln_padding_key;
    private RecyclerView rv_comment_detail_product, rating_user;
    private Product product;
    private User user;
    private int promo, check_fv, un_check_fv, fd_id;
    private boolean b_check_fv = false, bought = false;
    private String cate_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        check_fv = R.drawable.ic_baseline_favorite_24;
        un_check_fv = R.drawable.ic_baseline_favorite_border_24;
        RelativeLayout rl_root = view.findViewById(R.id.ln_root);
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        appBarLayoutDetailProduct = view.findViewById(R.id.appBarLayoutDetailProduct);
        img_menu_detail_product = view.findViewById(R.id.img_menu_detail_product);
        img_shopping_cart_detail_product = view.findViewById(R.id.img_shopping_cart_detail_product);
        img_home_detail_product = view.findViewById(R.id.img_home_detail_product);
        img_search_detail_product = view.findViewById(R.id.img_search_detail_product);
        img_back_detail_product = view.findViewById(R.id.img_back_detail_product);
        img_detail_product = view.findViewById(R.id.img_detail_product);
        txt_name_detail_product = view.findViewById(R.id.txt_name_detail_product);
        txt_price_detail_product = view.findViewById(R.id.txt_price_detail_product);
        txt_price_original_detail_product = view.findViewById(R.id.txt_price_original_detail_product);
        txt_promotion_detail_product = view.findViewById(R.id.txt_promotion_detail_product);
        txt_number_rating_detail_product = view.findViewById(R.id.txt_number_rating_detail_product);
        rating_detail_product = view.findViewById(R.id.rating_detail_product);
        txt_category_detail_product = view.findViewById(R.id.txt_category_detail_product);
        txt_warranty_detail_product = view.findViewById(R.id.txt_warranty_detail_product);
        txt_accessories_detail_product = view.findViewById(R.id.txt_accessories_detail_product);
        txt_conditional_detail_product = view.findViewById(R.id.txt_conditional_detail_product);
        txt_description_detail_product = view.findViewById(R.id.txt_description_detail_product);
        txt_rating_detail_product = view.findViewById(R.id.txt_rating_detail_product);
        txt_number_rating_detail_product2 = view.findViewById(R.id.txt_number_rating_detail_product2);
        ln_rating_user = view.findViewById(R.id.ln_rating_user);
        ln_padding_key = view.findViewById(R.id.ln_padding_key);
        rating_detail_product2 = view.findViewById(R.id.rating_detail_product2);
        progress_5_star = view.findViewById(R.id.progress_5_star);
        progress_4_star = view.findViewById(R.id.progress_4_star);
        progress_3_star = view.findViewById(R.id.progress_3_star);
        progress_2_star = view.findViewById(R.id.progress_2_star);
        progress_1_star = view.findViewById(R.id.progress_1_star);
        btn_add_to_cart = view.findViewById(R.id.btn_add_to_cart);
        edt_comment = view.findViewById(R.id.edt_comment);
        btn_send_comment  = view.findViewById(R.id.btn_send_comment);
        txt_rating_user  = view.findViewById(R.id.txt_rating_user);
        txt_login  = view.findViewById(R.id.txt_login);
        rating_user  = view.findViewById(R.id.rating_user);
        ln_comment  = view.findViewById(R.id.ln_comment);
        rv_comment_detail_product  = view.findViewById(R.id.rv_comment_detail_product);
        btn_send_star  = view.findViewById(R.id.btn_send_star);
        detail_favorite_product = view.findViewById(R.id.detail_favorite_product);
        appBarLayoutDetailProduct.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                int o = scrollRange + verticalOffset;
                if (o>=0&&o<100){
                    img_menu_detail_product.setBackgroundColor(0x00000000);
                    img_menu_detail_product.setColorFilter(0xFF0021C8);
                    img_shopping_cart_detail_product.setBackgroundColor(0x00000000);
                    img_shopping_cart_detail_product.setColorFilter(0xFF0021C8);
                    img_home_detail_product.setBackgroundColor(0x00000000);
                    img_home_detail_product.setColorFilter(0xFF0021C8);
                    img_search_detail_product.setBackgroundColor(0x00000000);
                    img_search_detail_product.setColorFilter(0xFF0021C8);
                    img_back_detail_product.setBackgroundColor(0x00000000);
                    img_back_detail_product.setColorFilter(0xFF0021C8);
                }else{
                    img_menu_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_menu_detail_product.setColorFilter(0xFFFFFFFF);
                    img_shopping_cart_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_shopping_cart_detail_product.setColorFilter(0xFFFFFFFF);
                    img_home_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_home_detail_product.setColorFilter(0xFFFFFFFF);
                    img_search_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_search_detail_product.setColorFilter(0xFFFFFFFF);
                    img_back_detail_product.setBackgroundResource(R.drawable.bg_oval);
                    img_back_detail_product.setColorFilter(0xFFFFFFFF);
                }
            }
        });
        img_back_detail_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        Bundle extras = getArguments();
        product = (Product) extras.getSerializable("product");
        Promotion promotion = (Promotion) extras.getSerializable("promotion");
        String rating = extras.getString("rating");
        Glide.with(getContext()).load(product.getProduct_img()).into(img_detail_product);
        String dsc = product.getProduct_description().replace("<p>", "");
        dsc = dsc.replace("</p>", "");
        txt_name_detail_product.setText(product.getProduct_name() + " là " + dsc);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        promo = 0;
        if (promotion!=null){
            promo = promotion.getPromotion_infor();
        }
        String price = formatter.format(product.getProduct_price()-(product.getProduct_price()*promo)/100);
        Currency currency = Currency.getInstance("VND");
        String vnd = currency.getSymbol();
        txt_price_detail_product.setText(price+" "+vnd);
        txt_price_original_detail_product.setText(Html.fromHtml("<strike>"+formatter.format(product.getProduct_price())+vnd+"</strike>"));
        if(promo!=0){
            txt_promotion_detail_product.setText("- " + promotion.getPromotion_infor()+"%");
        }else{
            txt_promotion_detail_product.setText("");
        }
        rating_detail_product.setRating(Float.parseFloat(rating));
        rating_detail_product2.setRating(Float.parseFloat(rating));
        getRatingAll();
        txt_warranty_detail_product.setText(product.getProduct_warranty());
        txt_accessories_detail_product.setText(product.getProduct_accessories());
        txt_conditional_detail_product.setText(product.getProduct_condition());
        txt_description_detail_product.setText(dsc);
        Common.compositeDisposable.add(Common.it2ShopAPI.getEditCategory(product.getProduct_cate())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Category>() {
            @Override
            public void accept(Category responseCategory) throws Exception {
                if (responseCategory!=null){
                    txt_category_detail_product.setText(responseCategory.getCategory_name()+"");
                    cate_name = responseCategory.getCategory_name();
                }
            }
        }));
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                ItemCart itemCart = new ItemCart();
                List<ItemCart> items = T2ShopDatabase.getInstance(getContext()).itemCartDAO().getItems();
                for(int i = 0; i < items.size(); i++){
                    if (items.get(i).getProduct_id()==product.getProduct_id()){
                        count++;
                        itemCart = items.get(i);
                        itemCart.setAmount(itemCart.getAmount()+1);
                    }
                }
                if (count==0){
                    ItemCart item = new ItemCart();
                    item.setProduct_id(product.getProduct_id());
                    item.setProduct_name(product.getProduct_name());
                    item.setProduct_price(product.getProduct_price());
                    item.setProduct_description(product.getProduct_description());
                    item.setProduct_img(product.getProduct_img());
                    item.setPromotion_infor(promo);
                    item.setProduct_amount(product.getProduct_amount());
                    item.setAmount(1);
                    T2ShopDatabase.getInstance(getContext()).itemCartDAO().insert(item);
                }else{
                    T2ShopDatabase.getInstance(getContext()).itemCartDAO().update(itemCart);
                }
                Common2.showDialogAutoClose(getContext(), "Thêm sản phẩm vào giỏ hàng thành công!");
            }
        });
        img_shopping_cart_detail_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                CartFragment cartFragment = new CartFragment();
                transaction.setCustomAnimations(R.anim.anim_fade_in, R.anim.anim_fade_out, R.anim.anim_fade_in, R.anim.anim_fade_out);
                transaction.replace(R.id.main_frame, cartFragment);
                transaction.addToBackStack(CartFragment.TAG);
                transaction.commit();
            }
        });
        user = T2ShopDatabase.getInstance(getContext()).userDAO().getItems();
        if (user==null){
            txt_login.setVisibility(View.VISIBLE);
            rating_user.setVisibility(View.INVISIBLE);
            ln_comment.setVisibility(View.INVISIBLE);
            ln_rating_user.setVisibility(View.GONE);
        }else{
            txt_login.setVisibility(View.INVISIBLE);
            checkBought();
        }
        btn_send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateComment()){
                    SweetAlertDialog dialog = Common2.loadingDialog(getContext(), "Xin chờ..");
                    dialog.show();
                    Common.compositeDisposable.add(Common.it2ShopAPI.addComment(user.getUser_id(), product.getProduct_id(), edt_comment.getEditText().getText().toString().trim())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseMessage>() {
                        @Override
                        public void accept(ResponseMessage message) throws Exception {
                            dialog.dismiss();
                            if (message.getMessage()!=null){
                                Common2.showDialogAutoClose(getContext(), "Đã bình luận!");
                                getComment();
                                edt_comment.getEditText().setText("");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Common2.errorDialog(getContext(), "Vui lòng kiểm tra kết nối Internet!");
                        }
                    }));
                }
            }
        });
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                TabLayout.Tab tab = MainActivity.tabLayout.getTabAt(4);
                tab.select();
            }
        });
        getComment();
        getRating();
        rating();
        keyboard(view);
        if(user!=null){
            handlefavorite();
            handleRecommend();
        }
        img_home_detail_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout.Tab tab = MainActivity.tabLayout.getTabAt(0);
                tab.select();
                getFragmentManager().popBackStack();
            }
        });
        img_search_detail_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout.Tab tab = MainActivity.tabLayout.getTabAt(2);
                tab.select();
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void checkBought() {
        Common.compositeDisposable.add(Common.it2ShopAPI.checkAllowRating(user.getUser_id(), product.getProduct_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseOrder>() {
                    @Override
                    public void accept(ResponseOrder responseOrder) throws Exception {
                        if (responseOrder.getOrder().size()>0){
                            rating_user.setVisibility(View.VISIBLE);
                            ln_comment.setVisibility(View.VISIBLE);
                            ln_rating_user.setVisibility(View.VISIBLE);
                        }else{
                            rating_user.setVisibility(View.INVISIBLE);
                            ln_comment.setVisibility(View.INVISIBLE);
                            ln_rating_user.setVisibility(View.GONE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Common2.showDialogAutoClose(getContext(), "Vui lòng kết nối INTERNET!");
                    }
                }));
    }

    private void handleRecommend() {
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                addRecommend();
            }
        };
        handler.postDelayed(r, 10000);
    }

    private void addRecommend() {
        Common.compositeDisposable.add(Common.it2ShopAPI.addRecommend(user.getUser_id(), cate_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(ResponseMessage message) throws Exception {
                        if (message.getMessage().equals("success")){
                        }else{
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Common2.showDialogAutoClose(getContext(), "Vui lòng kết nối INTERNET!");
                    }
                }));
    }

    private void handlefavorite() {
        getFavorite();
        detail_favorite_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b_check_fv){
                    removeFavorite();
                }else{
                    addFavorite();
                }
            }
        });
    }

    private void removeFavorite() {
        SweetAlertDialog dialog = Common2.loadingDialog(getContext(), "Xin chờ..");
        dialog.show();
        Common.compositeDisposable.add(Common.it2ShopAPI.removeFavorite(fd_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(ResponseMessage message) throws Exception {
                        dialog.dismiss();
                        if (message.getMessage()!=null){
                            getFavorite();
                            Common2.showDialogAutoClose(getContext(), "Đã bỏ yêu thích!");
                        }else{
                            Common2.showDialogAutoClose(getContext(), "Đã xảy ra lỗi!");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Common2.showDialogAutoClose(getContext(), "Vui lòng kết nối INTERNET!");
                    }
                }));
    }

    private void addFavorite() {
        SweetAlertDialog dialog = Common2.loadingDialog(getContext(), "Xin chờ..");
        dialog.show();
        Common.compositeDisposable.add(Common.it2ShopAPI.addFavorite(user.getUser_id(), product.getProduct_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(ResponseMessage message) throws Exception {
                        dialog.dismiss();
                        if (message.getMessage().equals("success")){
                            getFavorite();
                            Common2.showDialogAutoClose(getContext(), "Đã thêm vào yêu thích!");
                        }else{
                            Common2.showDialogAutoClose(getContext(), "Đã xảy ra lỗi!");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Common2.showDialogAutoClose(getContext(), "Vui lòng kết nối INTERNET!");
                    }
                }));
    }

    private void getFavorite() {
        Common.compositeDisposable.add(Common.it2ShopAPI.getFavorite(user.getUser_id(), product.getProduct_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseFavorite>() {
                    @Override
                    public void accept(ResponseFavorite responseFavorite) throws Exception {
                        if (responseFavorite.getFavorites()!=null&&responseFavorite.getFavorites().size()>0){
                            detail_favorite_product.setImageResource(check_fv);
                            b_check_fv = true;
                            fd_id = responseFavorite.getFavorites().get(0).getFP_id();
                        }else{
                            fd_id = -1;
                            detail_favorite_product.setImageResource(un_check_fv);
                            b_check_fv = false;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Common2.showDialogAutoClose(getContext(), "Vui lòng kết nối INTERNET!");
                    }
                }));
    }

    private void keyboard(View view) {

    }

    private void getRatingAll() {
        Common.compositeDisposable.add(Common.it2ShopAPI.getRatingAll(product.getProduct_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseRatingAll>() {
                    @Override
                    public void accept(ResponseRatingAll responseRatingAll) throws Exception {
                        if (responseRatingAll.getArrcount().get(0)==0) {
                            txt_number_rating_detail_product.setText("( 0 đánh giá)");
                            txt_number_rating_detail_product2.setText("(Chưa có đánh giá nào cả)");
                        } else {
                            txt_number_rating_detail_product.setText("("+responseRatingAll.getArrcount().get(0)+" đánh giá)");
                            txt_number_rating_detail_product2.setText("("+responseRatingAll.getArrcount().get(0)+" đánh giá)");
                            txt_rating_detail_product.setText(responseRatingAll.getArrcount().get(0)+"");
                            progress_1_star.setProgress((responseRatingAll.getArrcount().get(1)*100)/responseRatingAll.getArrcount().get(0));
                            progress_2_star.setProgress((responseRatingAll.getArrcount().get(2)*100)/responseRatingAll.getArrcount().get(0));
                            progress_3_star.setProgress((responseRatingAll.getArrcount().get(3)*100)/responseRatingAll.getArrcount().get(0));
                            progress_4_star.setProgress((responseRatingAll.getArrcount().get(4)*100)/responseRatingAll.getArrcount().get(0));
                            progress_5_star.setProgress((responseRatingAll.getArrcount().get(5)*100)/responseRatingAll.getArrcount().get(0));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "Vui lòng kiểm tra kết nối INTERNET", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void getRating(){
        if (user!=null) {
            Common.compositeDisposable.add(Common.it2ShopAPI.getRating(user.getUser_id(), product.getProduct_id())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseMessage>() {
                        @Override
                        public void accept(ResponseMessage message) throws Exception {
                            if (message.getMessage() != null) {
                                RatingAdapter.amount_star = Integer.parseInt(message.getMessage());
                                RatingAdapter ratingAdapter = new RatingAdapter(getContext());
                                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
                                rating_user.setLayoutManager(layoutManager);
                                rating_user.setAdapter(ratingAdapter);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                        }
                    }));
        }
    }

    private void rating() {
        switch (RatingAdapter.amount_star){
            case 1:
                txt_rating_user.setText("Rất tệ!");
                txt_rating_user.setTextColor(0xFFFF0000);
                break;
            case 2:
                txt_rating_user.setText("Tệ!");
                txt_rating_user.setTextColor(0xFFFF7B07);
                break;
            case 3:
                txt_rating_user.setText("Tạm ổn!");
                txt_rating_user.setTextColor(0xFFFFC107);
                break;
            case 4:
                txt_rating_user.setText("Ổn!");
                txt_rating_user.setTextColor(0xFFF7FF07);
                break;
            case 5:
                txt_rating_user.setText("Tuyệt vời!");
                txt_rating_user.setTextColor(0xFF8BFF07);
                break;
            default:
                txt_rating_user.setText("");
                break;
        }
        btn_send_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog dialog = Common2.loadingDialog(getContext(), "Xin chờ..");
                dialog.show();
                Common.compositeDisposable.add(Common.it2ShopAPI.addRating(user.getUser_id(), product.getProduct_id(), RatingAdapter.amount_star)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseMessage>() {
                    @Override
                    public void accept(ResponseMessage message) throws Exception {
                        dialog.dismiss();
                        if (message.getMessage()!=null){
                            getRatingAll();
                            Common2.showDialogAutoClose(getContext(), "Cảm ơn bạn đã đánh giá!");
                        }else{
                            Common2.showDialogAutoClose(getContext(), "Đã xảy ra lỗi!");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Common2.showDialogAutoClose(getContext(), "Vui lòng kết nối INTERNET!");
                    }
                }));
            }
        });
    }

    private void getComment() {
        Common.compositeDisposable.add(Common.it2ShopAPI.getComment(product.getProduct_id())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ResponseComment>() {
            @Override
            public void accept(ResponseComment responseComment) throws Exception {
                CommentAdapter commentAdapter = new CommentAdapter(getContext(), responseComment.getComments());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rv_comment_detail_product.setLayoutManager(layoutManager);
                rv_comment_detail_product.setAdapter(commentAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Common2.errorDialog(getContext(), "Vui lòng kiểm tra kết nối Internet!");
            }
        }));
    }

    private boolean validateComment() {
        if (edt_comment.getEditText().getText().toString().trim().length()==0){
            edt_comment.setError("Vui lòng nhập bình luận!");
            return false;
        }
        edt_comment.setError(null);
        return true;
    }
}