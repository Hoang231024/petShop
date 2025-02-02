package fpoly.truongtqph41980.petshop.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fpoly.truongtqph41980.petshop.Dao.DonHangDao;
import fpoly.truongtqph41980.petshop.Model.DonHang;
import fpoly.truongtqph41980.petshop.R;
import fpoly.truongtqph41980.petshop.adapter.Adapter_lich_su_don_hang;
import fpoly.truongtqph41980.petshop.adapter.adapter_don_hang;
import fpoly.truongtqph41980.petshop.databinding.FragmentFrgLichSuDonHangBinding;


public class frg_lich_su_don_hang extends Fragment {



    public frg_lich_su_don_hang() {
        // Required empty public constructor
    }
FragmentFrgLichSuDonHangBinding binding;
    private ArrayList<DonHang> list = new ArrayList<>();
    private DonHangDao dao;
    private Adapter_lich_su_don_hang adapterDonHang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFrgLichSuDonHangBinding.inflate(inflater,container,false);
        SharedPreferences preferences = getActivity().getSharedPreferences("NGUOIDUNG", MODE_PRIVATE);
        int mand = preferences.getInt("mataikhoan", 0);

        dao = new DonHangDao(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rcvLichSuDonHang.setLayoutManager(layoutManager);
        list = dao.getDonHangByMaTaiKhoan(mand);
        adapterDonHang = new Adapter_lich_su_don_hang(list,getActivity());
        binding.rcvLichSuDonHang.setAdapter(adapterDonHang);
        adapterDonHang.setOnItemClick(new adapter_don_hang.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                DonHang donHang = list.get(position);
                int maDonHang = donHang.getMaDonHang();

                Bundle bundle = new Bundle();
                bundle.putInt("maDonHang", maDonHang);
                frg_ls_don_hang_chi_tiet frgLsDonHangChiTiet = new frg_ls_don_hang_chi_tiet();
                frgLsDonHangChiTiet.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayoutMain, frgLsDonHangChiTiet);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return binding.getRoot();
    }
}