using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace EduSafe
{
    public partial class FormUser : Form
    {
        DataBaseDataContext db = new DataBaseDataContext();
        int user_Id;
        string mode;
        public FormUser()
        {
            InitializeComponent();
        }

        void enableField(bool e)
        {
            tbNama.Enabled = !e;
            tbEmail.Enabled = !e;
            tbPassword.Enabled = !e;
            tbUsername.Enabled = !e;
            cboKelas.Enabled = !e;
        }

        void enableButton(bool e)
        {
            btnBuat.Visible = e;
            btnUbah.Visible = e;
            btnHapus.Visible = e;

            btnBatal.Visible = !e;
            btnSimpan.Visible = !e;
        }

        void showDataCbo()
        {
            var list = new List<string>();
            list.Add("-Pilih Kelas-");
            list.Add("X");
            list.Add("XI");
            list.Add("XII");

            cboKelas.DataSource = list;
        }

        void enableFieldAndButton(bool e)
        {
            enableField(e);
            enableButton(e);
        }

        void showData()
        {
            dgvData.Columns.Clear();

            var query = db.Users.Where(x => x.username.StartsWith(tbSearch.Text) && x.delete_at == null).ToList()
                .Select(x => new
            {
                x.nama,
                x.username,
                x.email,
                x.password,
                created_at = x.created_at.ToString("dddd MM yyyy"),
                updated_at = x.update_at?.ToString("dddd MM yyyy") ?? "-",
                x.kelas,
                x.id
            }).ToList();

            dgvData.DataSource = query;
            dgvData.Columns["id"].Visible = false;
        }

        void showDataThread()
        {
            dgvDataThread.Columns.Clear();

            var query = db.Threads.Where(x => x.user_Id == user_Id).ToList()
                .Select(x => new
                {
                    x.judul,
                    x.isi,
                    x.status,
                    created_at = x.created_at.ToString("dddd MM yyyy"),
                    updated_at = x.update_at?.ToString("dddd MM yyyy") ?? "-",
                }).ToList();

            dgvDataThread.DataSource = query;
        }

        private void FormUser_Load(object sender, EventArgs e)
        {
            Support.enable(this);
            showData();
            showDataCbo();
            enableFieldAndButton(true);
        }

        private void dgvData_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                user_Id = e.RowIndex;
                user_Id = (int) dgvData.Rows[e.RowIndex].Cells["id"].Value;
                tbNama.Text = dgvData.Rows[e.RowIndex].Cells["nama"].Value.ToString();
                tbEmail.Text = dgvData.Rows[e.RowIndex].Cells["email"].Value.ToString();
                tbPassword.Text = dgvData.Rows[e.RowIndex].Cells["password"].Value.ToString();
                tbUsername.Text = dgvData.Rows[e.RowIndex].Cells["username"].Value.ToString();
                cboKelas.Text = dgvData.Rows[e.RowIndex].Cells["kelas"].Value.ToString();

                showDataThread();
            }
        }

        private void btnBuat_Click(object sender, EventArgs e)
        {
            mode = "buat";
            enableFieldAndButton(false);
            Support.clear(this);
        }

        private void btnUbah_Click(object sender, EventArgs e)
        {
            if (tbNama.Text == string.Empty)
            {
                Support.msw("Click row !!");
                return;
            }

            mode = "ubah";
            enableFieldAndButton(false);
        }

        private void btnSimpan_Click(object sender, EventArgs e)
        {
            if (tbNama.Text == string.Empty || tbEmail.Text == string.Empty
                || tbUsername.Text == string.Empty ||tbPassword.Text == string.Empty
                || cboKelas.Text == "-Pilih Kelas-")
            {
                Support.msw("All fields must be filled");
                return;
            }

            if (tbPassword.Text.Length < 6)
            {
                Support.msw("Field password must be minimal 6 length");
                return;
            }

            try
            {
                if (mode == "buat")
                {
                    var query = new User();
                    query.username = tbUsername.Text;
                    query.nama = tbNama.Text;
                    query.password = tbPassword.Text;
                    query.email = tbEmail.Text;
                    query.kelas = cboKelas.Text;
                    query.created_at = DateTime.Now;

                    db.Users.InsertOnSubmit(query);
                    db.SubmitChanges();
                    Support.msi("Berhasil membuat data");
                    showData();
                    showDataThread();
                    enableFieldAndButton(true);
                    Support.clear(this);
                }

                if (mode == "ubah")
                {
                    var query = db.Users.FirstOrDefault(x => x.id == user_Id);
                    query.username = tbUsername.Text;
                    query.nama = tbNama.Text;
                    query.password = tbPassword.Text;
                    query.email = tbEmail.Text;
                    query.kelas = cboKelas.Text;
                    query.update_at = DateTime.Now;

                    db.SubmitChanges();
                    Support.msi("Berhasil mengubah data");
                    showData();
                    showDataThread();
                    enableFieldAndButton(true);
                    Support.clear(this);
                }

            } catch (Exception ex)
            {
                Support.mse(ex.Message);
            }
        }

        private void btnHapus_Click(object sender, EventArgs e)
        {
            if (tbNama.Text == string.Empty)
            {
                Support.msw("Click row !!");
                return;
            }

            var dialog = MessageBox.Show("Kamu yakin ingin menghapus data ini?", "Konfirmasi", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

            if (dialog == DialogResult.Yes)
            {
                var query = db.Users.FirstOrDefault(x => x.id == user_Id);
                query.username = tbUsername.Text;
                query.nama = tbNama.Text;
                query.password = tbPassword.Text;
                query.email = tbEmail.Text;
                query.kelas = cboKelas.Text;
                query.delete_at = DateTime.Now;

                db.Users.DeleteOnSubmit(query);
                db.SubmitChanges();
                Support.msi("Berhasil menghapus data");
                showData();
                showDataThread();
                enableFieldAndButton(true);
                Support.clear(this);
            }
        }

        private void tbSearch_TextChanged(object sender, EventArgs e)
        {
            showData();
        }
    }
}
