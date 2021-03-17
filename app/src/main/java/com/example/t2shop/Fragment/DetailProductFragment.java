package com.example.t2shop.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.t2shop.Common.Constants;
import com.example.t2shop.Model.Product;
import com.example.t2shop.Model.Promotion;
import com.example.t2shop.R;
import com.google.android.material.appbar.AppBarLayout;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class DetailProductFragment extends Fragment {
    private AppBarLayout appBarLayoutDetailProduct;
    private ImageView img_menu_detail_product, img_shopping_cart_detail_product, img_home_detail_product, img_search_detail_product,
            img_back_detail_product, img_detail_product;
    private TextView txt_name_detail_product, txt_price_detail_product;
    public static String TAG = DetailProductFragment.class.getName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        appBarLayoutDetailProduct = view.findViewById(R.id.appBarLayoutDetailProduct);
        img_menu_detail_product = view.findViewById(R.id.img_menu_detail_product);
        img_shopping_cart_detail_product = view.findViewById(R.id.img_shopping_cart_detail_product);
        img_home_detail_product = view.findViewById(R.id.img_home_detail_product);
        img_search_detail_product = view.findViewById(R.id.img_search_detail_product);
        img_back_detail_product = view.findViewById(R.id.img_back_detail_product);
        img_detail_product = view.findViewById(R.id.img_detail_product);
        txt_name_detail_product = view.findViewById(R.id.txt_name_detail_product);
        txt_price_detail_product = view.findViewById(R.id.txt_price_detail_product);
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
        Product product = (Product) extras.getSerializable("product");
        Promotion promotion = (Promotion) extras.getSerializable("promotion");
        Glide.with(getContext()).load(product.getProduct_img()).into(img_detail_product);
        String dsc = product.getProduct_description().replace("<p>", "");
        dsc = dsc.replace("</p>", "");
        txt_name_detail_product.setText(product.getProduct_name() + " là " + dsc);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(product.getProduct_price()-(product.getProduct_price()*Integer.parseInt(promotion.getPromotion_infor()))/100);
        txt_price_detail_product.setText(price+" đ");
        return view;
    }
}