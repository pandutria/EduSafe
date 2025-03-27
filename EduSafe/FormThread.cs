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
    public partial class FormThread : Form
    {
        DataBaseDataContext db = new DataBaseDataContext();
        int thread_Id;
        string mode;
        public FormThread()
        {
            InitializeComponent();
        }

        void enableField(bool e)
        {
            tbNama.Enabled = !e;
            tbJudul.Enabled = !e;
            tbKelas.Enabled = !e;
            tbIsi.Enabled = !e;
            tbStatus.Enabled = !e;
        }

        void showData()
        {
            dgvData.Columns.Clear();

            var query = db.Threads.Where(x => x.judul.StartsWith(tbSearch.Text) && x.deleted_at == null).ToList()
                .Select(x => new
                {
                    user = x.User.nama,
                    kelas = x.User.kelas,
                    x.judul,
                    x.isi,
                    x.status,
                    created_at = x.created_at.ToString("dddd MM yyyy"),
                    updated_at = x.update_at?.ToString("dddd MM yyyy") ?? "-",
                    x.id
                }).ToList();

            dgvData.DataSource = query;
            dgvData.Columns["id"].Visible = false;
        }

        void showDataComment()
        {
            dgvDataComment.Columns.Clear();

            var query = db.Comments.Where(x => x.thread_Id == thread_Id && x.delete_at == null).ToList()
                .Select(x => new
                {
                    comment = x.comment1,
                    user = x.User.nama,
                    kelas = x.User.kelas,
                    created_at = x.created_at.ToString("dddd MM yyyy"),
                    updated_at = x.update_at?.ToString("dddd MM yyyy") ?? "-",
                }).ToList();

            dgvDataComment.DataSource = query;
        }

        private void FormThread_Load(object sender, EventArgs e)
        {
            Support.enable(this);
            showData();
            enableField(true);
            btnAktif.Visible = btnHapus.Visible = false;
        }

        private void dgvData_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                thread_Id = e.RowIndex;
                thread_Id = (int)dgvData.Rows[e.RowIndex].Cells["id"].Value;
                tbNama.Text = dgvData.Rows[e.RowIndex].Cells["user"].Value.ToString();
                tbJudul.Text = dgvData.Rows[e.RowIndex].Cells["judul"].Value.ToString();
                tbKelas.Text = dgvData.Rows[e.RowIndex].Cells["kelas"].Value.ToString();
                tbIsi.Text = dgvData.Rows[e.RowIndex].Cells["isi"].Value.ToString();
                tbStatus.Text = dgvData.Rows[e.RowIndex].Cells["status"].Value.ToString();

                if (tbStatus.Text == "aktif")
                {
                    btnAktif.Visible = false;
                    btnHapus.Visible = true;
                } else
                {
                    btnAktif.Visible=true;
                    btnHapus.Visible=false;
                }

                showDataComment();
            }
        }

        private void tbSearch_TextChanged(object sender, EventArgs e)
        {
            showData();
        }

        private void btnHapus_Click(object sender, EventArgs e)
        {
            if (tbNama.Text == string.Empty)
            {
                Support.msw("Click row!!");
                return;
            }

            var query = db.Threads.FirstOrDefault(x => x.id == thread_Id);

            if (query != null)
            {
                query.status = "tidak aktif";

                db.SubmitChanges();
                Support.msi("Berhasil mengubah status data menjadi tidak aktif");

                Support.clear(this);
                showData();
                showDataComment();
                btnAktif.Visible = btnHapus.Visible = false;
            }

        }

        private void btnAktif_Click(object sender, EventArgs e)
        {
            if(tbNama.Text == string.Empty)
            {
                Support.msw("Click row!!");
                return;
            }

            var query = db.Threads.FirstOrDefault(x => x.id == thread_Id);

            if (query != null)
            {
                query.status = "aktif";

                db.SubmitChanges();
                Support.msi("Berhasil mengubah status data menjadi aktif");

                Support.clear(this);
                showData();
                showDataComment();
                btnAktif.Visible = btnHapus.Visible = false;

            }
        }
    }
}
