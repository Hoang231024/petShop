package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DanhGiaDao;
import fpoly.truongtqph41980.petshop.Dao.DonHangChiTietDao;
import fpoly.truongtqph41980.petshop.Model.DanhGia;
import fpoly.truongtqph41980.petshop.Model.DonHangChiTiet;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.adapter_danh_gia;
import fpoly.truongtqph41980.petshop.adapter.adapter_don_hang_chi_tiet;
import fpoly.truongtqph41980.petshop.adapter.adapter_ls_don_hang_chi_tiet;
import fpoly.truongtqph41980.petshop.databinding.DialogDanhGiaBinding;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgLsDonHangChiTietBinding;


public class frg_ls_don_hang_chi_tiet extends Fragment {

    public frg_ls_don_hang_chi_tiet() {
        // Required empty public constructor
    }

    FragmentFrgLsDonHangChiTietBinding binding;
    private ArrayList<DonHangChiTiet> list = new ArrayList<>();

    private adapter_ls_don_hang_chi_tiet adapterDonHangChiTiet;
    DonHangChiTietDao chiTietDao;
    DanhGiaDao danhGiaDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgLsDonHangChiTietBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        danhGiaDao = new DanhGiaDao(getContext());


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvLichSuDonHang.setLayoutManager(layoutManager);
        chiTietDao = new DonHangChiTietDao(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        int mand = sharedPreferences.getInt("mataikhoan", 0);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int maDonHang = bundle.getInt("maDonHang", 0);
            Log.d("Mã đơn hàng", String.valueOf(maDonHang));
            if (maDonHang != 0) {
                list = chiTietDao.getChiTietDonHangByMaDonHang(maDonHang);
                adapterDonHangChiTiet = new adapter_ls_don_hang_chi_tiet(list, getContext());
                binding.rcvLichSuDonHang.setAdapter(adapterDonHangChiTiet);
                adapterDonHangChiTiet.setonAddDanhia(new adapter_ls_don_hang_chi_tiet.OnAddDanhGia() {
                    @Override
                    public void onAddDanhia(DonHangChiTiet donHangChiTiet) {


                        LayoutInflater layoutInflater =getLayoutInflater();
                        DialogDanhGiaBinding dialogDanhGiaBinding = DialogDanhGiaBinding.inflate(layoutInflater);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setView(dialogDanhGiaBinding.getRoot());
                        AlertDialog dialogD = builder.create();
                        dialogD.show();


                        LocalDate currentDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String ngayHienTai = currentDate.format(formatter);


                        dialogDanhGiaBinding.btnthembinhluan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String danhgia = dialogDanhGiaBinding.eddanhgia.getText().toString();
                                String nhanxet = dialogDanhGiaBinding.ednhanxet.getText().toString();
                                if (danhgia.isEmpty()) {
                                    dialogDanhGiaBinding.eddanhgia.setError("Vui lòng không để trống");
                                } else {
                                    dialogDanhGiaBinding.eddanhgia.setError(null);
                                }
                                if (nhanxet.isEmpty()) {
                                    dialogDanhGiaBinding.ednhanxet.setError("Vui lòng không để trống");
                                } else {
                                    dialogDanhGiaBinding.ednhanxet.setError(null);
                                }
                                if (dialogDanhGiaBinding.eddanhgia.getError() == null && dialogDanhGiaBinding.ednhanxet.getError() == null) {
                                    Log.d("danhgiaaaaaa: ", danhgia);
                                    Log.d("nhanxettt: ", nhanxet);
                                    danhGiaDao.addDanhGia(mand,donHangChiTiet.getMaSanPham(), danhgia, nhanxet, ngayHienTai);
                                    Toast.makeText(getContext(), "Thêm  thành công", Toast.LENGTH_SHORT).show();
                                    dialogD.dismiss();
                                }
                            }
                        });
                    }
                });
            }
        }

        binding.imgBackLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frg_lich_su_don_hang frgLichSuDonHang = new frg_lich_su_don_hang();//fragment được chuyển đến sau khi ấn
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutMain, frgLichSuDonHang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();
    }
}