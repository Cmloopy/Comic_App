
create database quanlyphongkham;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

use quanlyphongkham;


CREATE TABLE `donthuoc` (
  `maDonThuoc` int(6) NOT NULL,
  `maBenhNhan` varchar(6) NOT NULL,
  `maDuocSi` varchar(6) NOT NULL COMMENT 'Người lấy thuốc cho bệnh nhân',
  `maBacSi` varchar(6) NOT NULL COMMENT 'Người bác sĩ kê đơn',
  `tenThuoc` varchar(50) NOT NULL,
  `soLuong` int(11) NOT NULL,
  `ngayLayThuoc` date NOT NULL,
  `tongTien` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



INSERT INTO `donthuoc` (`maDonThuoc`, `maBenhNhan`, `maDuocSi`, `maBacSi`, `ngayLayThuoc`, `tongTien`) VALUES
(1, 'BN01', 'DS01', 'BS02', '2023-12-10', 20000),
(2, 'BN02', 'DS01', 'BS01', '2023-02-11', 10000),
(3, 'BN03', 'DS01', 'BS03', '2023-27-11', 40000),
(4, 'BN04', 'DS01', 'BS03', '2023-28-11', 18000),
(5, 'BN05', 'DS01', 'BS02', '2023-29-11', 30000);

/* CREATE TABLE `donthuocchitiet` (
  `maDonThuocChiTiet` int(6) NOT NULL,
  `maDonThuoc` int(6) NOT NULL,
  `maThuoc` varchar(6) NOT NULL,
  `soLuong` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8; */



CREATE TABLE `nhanvien` (
  `maNhanVien` varchar(6) NOT NULL COMMENT 'maNhanVien: NV0001',
  `hoTen` varchar(30) NOT NULL,
  `ngaySinh` date NOT NULL,
  `gioiTinh` varchar(11) NOT NULL,
  `chucVu` varchar(50) NOT NULL,
  `diaChi` varchar(200) NOT NULL,
  `soDienThoai` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `soCMND` varchar(50) NOT NULL,
  `password` varchar(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


INSERT INTO `nhanvien` (`maNhanVien`, `hoTen`, `ngaySinh`, `gioiTinh`, `chucVu`, `diaChi`, `soDienThoai`, `email`, `soCMND`, `password`) VALUES
('NV01', 'Trần Anh Sơn', '2003-03-13', 'Nam', 'Nhân viên lễ tân', 'Nam Định', '0127877777', 'sonta97@gmail.com', '123456789', '123'),
('BS01', 'Lê Ngọc Quyền', '2003-09-17', 'Nam', 'Bác sĩ đa khoa', 'Thanh Hóa', '0868999999', 'quyenln@gmail.com', '888888888', '123456'),
('BS02', 'Nguyễn Minh Quân', '2003-09-28', 'Nam', 'Bác sĩ chuyên khoa', 'Thanh Hóa', '0797888888', 'quannm@gmail.com', '1239123129', '123456'),
('BS03', 'Đặng Minh Anh', '2003-10-10', 'Nam', 'Bác  sĩ chuyên khoa', 'Hà Nội', '0868555555', 'anhdm@gmail.com', '999999999', '123456'),
('DS01', 'Lại Thế Hòa', '2003-12-12', 'Nam', 'Dược sĩ', 'Hà Nội', '0898456789', 'hoalt@gmail.com', '0294723842', '111111');


CREATE TABLE `phieukhamchitiet` (
  `maPhieuKham` varchar(6) NOT NULL,
  `maBenhNhan` varchar(6) NOT NULL,
  `maNhanVien` varchar(6) NOT NULL COMMENT 'mã của bác sĩ thực hiện',
  `ngayKham` date NOT NULL,
  `KhuyenNghi` varchar(200) DEFAULT NULL,
  `ketLuan` varchar(200) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `phieukhamchitiet` (`maPhieuKham`, `maBenhNhan`, `maNhanVien`, `ngayKham`, `KhuyenNghi`, `ketLuan`)
VALUES 
('PK0001', 'BN01', 'BS01', '2023-12-10', 'Uống thuốc theo đúng liều lượng', 'Không có vấn đề đặc biệt'),
('PK0002', 'BN02', 'BS02', '2023-02-11', 'Nghỉ ngơi đầy đủ', 'Dấu hiệu của mệt mỏi'),
('PK0003', 'BN03', 'BS03', '2023-27-11', 'Tập thể dục đều đặn', 'Chưa có vấn đề gì đặc biệt'),
('PK0004', 'BN04', 'BS02', '2023-28-11', 'Kiểm tra lại sau 2 tuần', 'Cần theo dõi tình trạng sức khỏe'),
('PK0005', 'BN05', 'BS03', '2023-29-11', NULL, 'Không có vấn đề đặc biệt');


CREATE TABLE `benhnhan` (
  `maBenhNhan` varchar(6) NOT NULL,
  `tenBenhNhan` varchar(30) NOT NULL,
  `maPhieuKham` varchar(6) NOT NULL COMMENT 'maPhieuKham: PK0001',
  `gioiTinh` varchar(6) NOT NULL,
  `ngaySinh` date NOT NULL,
  `ngheNghiep` varchar(30) NOT NULL,
  `tinhTrangHonNhan` varchar(30) NOT NULL,
  `diaChi` varchar(200) DEFAULT NULL,
   `soDienThoai` varchar(50) NOT NULL,
  `soCMND` varchar(30) NOT NULL,
  `tieuSuBenhAn` varchar(200) DEFAULT NULL COMMENT 'tiền sử bệnh của người nhà, bản thân',
  `TrieuChung` varchar(200) NOT NULL COMMENT 'Khi vào nhân viên lễ tân hỏi và ghi lại triệu chứng vào phần lí do khám',
  `ghiChu` varchar(200) DEFAULT NULL
 
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


INSERT INTO `benhnhan` (`maBenhNhan`, `tenBenhNhan`, `maPhieuKham`, `gioiTinh`, `ngaySinh`, `ngheNghiep`, `tinhTrangHonNhan`, `diaChi`, `soDienThoai`, `soCMND`, `tieuSuBenhAn`, `TrieuChung`, `ghiChu`)
VALUES 
('BN01', 'Nguyễn Văn A', 'PK0001', 'Nam', '1990-01-15', 'Nhân viên văn phòng', 'Đã kết hôn', 'Hà Nội', '0123456789', '123456789', 'Không có', 'Sốt và đau đầu', 'không'),
('BN02', 'Trần Thị B', 'PK0002', 'Nữ', '1985-05-20', 'Giáo viên', 'Đã ly hôn', 'Hồ Chí Minh', '0987654321', '987654321', 'Tiểu đường', 'Mệt mỏi và buồn nôn', 'không'),
('BN03', 'Lê Văn C', 'PK0003', 'Nam', '1998-09-10', 'Sinh viên', 'Độc thân', 'Đà Nẵng', '0111222333', '111222333', 'Asthma', 'Khó thở và ho', 'Ghi chú 3'),
('BN04', 'Phạm Thị D', 'PK0004', 'Nữ', '1975-12-05', 'Bác sĩ', 'Đã kết hôn', 'Hải Phòng', '0333444555', '333444555', 'Chảy máu mũi', 'Chảy máu mũi thường xuyên', 'không'),
('BN05', 'Ngô Văn E', 'PK0005', 'Nam', '1980-03-25', 'Kỹ sư', 'Độc thân', 'Cần Thơ', '0444555666', '444555666', 'Đau lưng', 'Đau lưng sau khi ngồi lâu', 'không');



CREATE TABLE `thuoc` (
  `maThuoc` varchar(6) NOT NULL,
  `tenThuoc` varchar(50) NOT NULL,
  `soLuong` int(11) NOT NULL,
  `donGia` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


INSERT INTO `thuoc` (`maThuoc`, `tenThuoc`, `soLuong`, `donGia`) VALUES
('PN1', 'panadon', 18, 5000),
('TX1', 'trixadon', 34, 7000),
('PT1', 'pentadon', 20, 9000),
('PT2', 'pentaphacon', 14, 10000),
('QR1', 'quaradon', 12, 8000);



ALTER TABLE `benhnhan`
  ADD PRIMARY KEY (`maBenhNhan`);


ALTER TABLE `donthuoc`
  ADD PRIMARY KEY (`maDonThuoc`),
  ADD KEY `fk2` (`maBenhNhan`),
  ADD KEY `fk3` (`maDuocSi`),
  ADD KEY `fk4` (`maBacSi`);


ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`maNhanVien`);


ALTER TABLE `phieukhamchitiet`
  ADD PRIMARY KEY (`maPhieuKham`),
  ADD KEY `fk1` (`maBenhNhan`),
  ADD KEY `fk2` (`maNhanVien`),
  ADD KEY `fk3` (`maPhieuKham`);


ALTER TABLE `thuoc`
  ADD PRIMARY KEY (`tenThuoc`);


ALTER TABLE `donthuoc`
  MODIFY `maDonThuoc` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

COMMIT;
