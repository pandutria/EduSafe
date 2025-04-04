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
    public partial class FormMain : Form
    {
        public FormMain()
        {
            InitializeComponent();
        }

        private void FormMain_Load(object sender, EventArgs e)
        {
            Support.enable(this);
        }

        private void btnUser_Click(object sender, EventArgs e)
        {
            btnUser.BackColor = Color.FromArgb(251, 168, 52);
            btnThread.BackColor = Color.DodgerBlue;

            panelContainer.Controls.Clear();
            var f = new FormUser();
            f.TopLevel = false;

            panelContainer.Controls.Add(f);
            f.Show();

        }

        private void btnThread_Click(object sender, EventArgs e)
        {
            btnThread.BackColor = Color.FromArgb(251, 168, 52);
            btnUser.BackColor = Color.DodgerBlue;

            panelContainer.Controls.Clear();
            var f = new FormThread();
            f.TopLevel = false;

            panelContainer.Controls.Add(f);
            f.Show();
        }
    }
}
